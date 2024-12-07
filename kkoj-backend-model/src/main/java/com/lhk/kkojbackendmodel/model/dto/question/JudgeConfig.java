package com.lhk.kkojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * 判题配置
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    public long timeLimit;

    /**
     * 内存限制（kb）
     */
    public long memoryLimit;

    /**
     * 堆栈限制（kb）
     */
    public long stackLimit;

}
