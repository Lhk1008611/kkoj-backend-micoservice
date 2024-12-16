package com.lhk.kkojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.lhk.kkojbackendmodel.model.codesandbox.model.JudgeInfo;
import com.lhk.kkojbackendmodel.model.dto.question.JudgeCase;
import com.lhk.kkojbackendmodel.model.dto.question.JudgeConfig;
import com.lhk.kkojbackendmodel.model.entity.Question;
import com.lhk.kkojbackendmodel.model.enums.JudgeInfoMessageEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 默认判题策略实现类
 */
@Service
public class JavaJudgeStrategyImpl implements JudgeStrategy {
    @Override
    public JudgeInfo judge(JudgeContext judgeContext) {

        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
        JudgeInfo reponseJudgeInfo = new JudgeInfo();
        reponseJudgeInfo.setMemory(memory);
        reponseJudgeInfo.setTime(time);
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        // 校验输入输出用例
        if (outputList.size()!=inputList.size()){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            reponseJudgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return reponseJudgeInfo;
        }
        // 获取题目的标准输出用例
        List<JudgeCase> judgeCaselist = judgeContext.getJudgeCaselist();
        for (int i = 0; i < judgeCaselist.size(); i++) {
            String output = outputList.get(i);
            String standardOutput = judgeCaselist.get(i).getOutput();
            if (!output.equals(standardOutput)){
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                reponseJudgeInfo.setMessage(judgeInfoMessageEnum.getValue());
                return reponseJudgeInfo;
            }
        }
        // 校验题目执行限制
        Question question = judgeContext.getQuestion();
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        long timeLimit = judgeConfig.getTimeLimit();
        long memoryLimit = judgeConfig.getMemoryLimit();
        // 若编译时间为1s，则真正的执行时间需要减去1s

        if (time > timeLimit ){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            reponseJudgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return reponseJudgeInfo;
        }
        if (memory > memoryLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            reponseJudgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return reponseJudgeInfo;
        }
        reponseJudgeInfo.setMessage(judgeInfoMessageEnum.getValue());
        return reponseJudgeInfo;
    }
}
