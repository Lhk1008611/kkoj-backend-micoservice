package com.lhk.kkojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.lhk.kkojbackendcommon.common.ErrorCode;
import com.lhk.kkojbackendcommon.exception.BusinessException;
import com.lhk.kkojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.lhk.kkojbackendjudgeservice.judge.codesandbox.CodeSandBoxFactory;
import com.lhk.kkojbackendjudgeservice.judge.codesandbox.CodeSandBoxProxy;
import com.lhk.kkojbackendjudgeservice.judge.strategy.JudgeContext;
import com.lhk.kkojbackendmodel.model.codesandbox.model.ExecuteCodeRequest;
import com.lhk.kkojbackendmodel.model.codesandbox.model.ExecuteCodeResponse;
import com.lhk.kkojbackendmodel.model.codesandbox.model.JudgeInfo;
import com.lhk.kkojbackendmodel.model.dto.question.JudgeCase;
import com.lhk.kkojbackendmodel.model.entity.Question;
import com.lhk.kkojbackendmodel.model.entity.QuestionSubmit;
import com.lhk.kkojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.lhk.kkojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题服务实现类
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type:example}")
    private String type;
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private JudgeManager judgeManager;
    @Override
    public QuestionSubmit JudgeQuestion(Long questionSubmitId) {
        // 获取题目提交信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目 {questionId} 不存在");
        }
        // 判断题目提交状态是否为等待中,不为等待中则抛出异常，防止重复判题
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题中");
        }
        // 题目提交状态为等待中,则更新题目提交状态为判题中，防止重复判题，相当于加锁
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(updateQuestionSubmit);
        if (!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交状态更新失败");
        }
        // 调用代码沙箱执行代码
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        // 使用代理模式进行增强
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy(codeSandBox);
        // 编程语言
        String language = questionSubmit.getLanguage();
        // 题目执行代码
        String code = questionSubmit.getCode();

        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaselist = JSONUtil.toList(judgeCase, JudgeCase.class);
        // 判题输入用例
        List<String> inputList = judgeCaselist.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        // 调用代码沙箱
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .language(language)
                .code(code)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBoxProxy.executeCode(executeCodeRequest);
        if (executeCodeResponse == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"沙箱返回结果为空");
        }
        // 验证沙箱返回结果
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(judgeInfo);
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaselist(judgeCaselist);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo responseJudgeInfo = judgeManager.judge(judgeContext);
        // 更新题目提交状态和判题信息
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        updateQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(responseJudgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(updateQuestionSubmit);
        if (!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交状态更新失败");
        }
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(responseJudgeInfo));
        questionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        return questionSubmit;
    }
}
