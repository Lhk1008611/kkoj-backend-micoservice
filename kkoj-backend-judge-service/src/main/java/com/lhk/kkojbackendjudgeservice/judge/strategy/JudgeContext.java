package com.lhk.kkojbackendjudgeservice.judge.strategy;

import com.lhk.kkojbackendmodel.model.codesandbox.model.JudgeInfo;
import com.lhk.kkojbackendmodel.model.dto.question.JudgeCase;
import com.lhk.kkojbackendmodel.model.entity.Question;
import com.lhk.kkojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 判题策略上下文
 * @author lhk
 */
@Data
public class JudgeContext {

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 输入用例列表
     */
    private List<String> inputList;

    /**
     * 经过代码沙箱后的输出用例列表
     */
    private List<String> outputList;

    /**
     * 题目信息
     */
    private Question question;

    /**
     * 标准判题输入输出用例列表 [输入用例，标准输出用例]
     */
    private List<JudgeCase> judgeCaselist;

    /**
     * 题目提交信息
     */
    private QuestionSubmit questionSubmit;

}
