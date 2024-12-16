package com.lhk.kkojbackendjudgeservice.judge.strategy;


import com.lhk.kkojbackendmodel.model.codesandbox.model.JudgeInfo;

/**
 * 判题策略接口
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo judge(JudgeContext judgeContext);
}
