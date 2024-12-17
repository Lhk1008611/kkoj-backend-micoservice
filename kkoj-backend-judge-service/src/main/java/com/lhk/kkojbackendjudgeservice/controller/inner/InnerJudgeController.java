package com.lhk.kkojbackendjudgeservice.controller.inner;

import com.lhk.kkojbackendjudgeservice.judge.JudgeService;
import com.lhk.kkojbackendmodel.model.entity.QuestionSubmit;
import com.lhk.kkojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 服务内部调用的接口实现
 */
@RestController("/inner")
public class InnerJudgeController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @PostMapping("/")
    @Override
    public QuestionSubmit JudgeQuestion(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.JudgeQuestion(questionSubmitId);
    }
}