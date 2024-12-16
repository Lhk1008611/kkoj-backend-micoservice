package com.lhk.kkojbackendjudgeservice.judge.codesandbox.impl;


import com.lhk.kkojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.lhk.kkojbackendmodel.model.codesandbox.model.ExecuteCodeRequest;
import com.lhk.kkojbackendmodel.model.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
