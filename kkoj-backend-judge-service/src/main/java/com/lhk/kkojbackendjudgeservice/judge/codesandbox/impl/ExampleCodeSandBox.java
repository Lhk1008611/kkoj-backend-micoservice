package com.lhk.kkojbackendjudgeservice.judge.codesandbox.impl;

import com.lhk.kkojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.lhk.kkojbackendmodel.model.codesandbox.model.ExecuteCodeRequest;
import com.lhk.kkojbackendmodel.model.codesandbox.model.ExecuteCodeResponse;
import com.lhk.kkojbackendmodel.model.codesandbox.model.JudgeInfo;
import com.lhk.kkojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.lhk.kkojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱，仅用于示例，无实际功能
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("示例代码沙箱执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMemory(200L);
        judgeInfo.setTime(100L);
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
