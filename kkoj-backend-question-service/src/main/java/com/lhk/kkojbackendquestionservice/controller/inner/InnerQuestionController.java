package com.lhk.kkojbackendquestionservice.controller.inner;

import com.lhk.kkojbackendmodel.model.entity.Question;
import com.lhk.kkojbackendmodel.model.entity.QuestionSubmit;
import com.lhk.kkojbackendquestionservice.service.QuestionService;
import com.lhk.kkojbackendquestionservice.service.QuestionSubmitService;
import com.lhk.kkojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 服务内部调用的接口实现
 */
@RestController
@RequestMapping("/inner")
public class InnerQuestionController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
       return questionService.getById(questionId);
    }
    @GetMapping("/question_submit/get")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }
    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

}