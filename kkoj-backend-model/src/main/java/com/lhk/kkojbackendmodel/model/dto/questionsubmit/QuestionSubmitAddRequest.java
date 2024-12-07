package com.lhk.kkojbackendmodel.model.dto.questionsubmit;


import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交代码
     */
    private String code;

    private static final long serialVersionUID = 1L;

}
