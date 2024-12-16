package com.lhk.kkojbackendjudgeservice.judge.codesandbox;

import com.lhk.kkojbackendmodel.model.codesandbox.model.ExecuteCodeRequest;
import com.lhk.kkojbackendmodel.model.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱代理类
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox {

    private CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱执行请求信息" + executeCodeRequest.toString());
        // 执行原始代码沙箱功能
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        if (executeCodeResponse != null) {
            log.info("代码沙箱执行响应信息" + executeCodeResponse.toString());
        } else {
            log.info("代码沙箱执行响应信息为空");
        }
        return executeCodeResponse;
    }
}
