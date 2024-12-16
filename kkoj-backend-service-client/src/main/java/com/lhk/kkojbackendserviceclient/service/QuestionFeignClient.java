package com.lhk.kkojbackendserviceclient.service;

import com.lhk.kkojbackendmodel.model.entity.Question;
import com.lhk.kkojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Lhk
 * @description 使用 OpenFeign 调用题目服务
 */
@FeignClient(name = "kkoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    @GetMapping("/get")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(QuestionSubmit questionSubmit);
}
