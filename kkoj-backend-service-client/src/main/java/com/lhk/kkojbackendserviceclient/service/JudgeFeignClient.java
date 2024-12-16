package com.lhk.kkojbackendserviceclient.service;


import com.lhk.kkojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用 OpenFeign 调用判题服务
 */
@FeignClient(name = "kkoj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * 执行判题
     * @param questionSubmitId 题目提交 id
     * @return
     */
    @PostMapping("/")
    QuestionSubmit JudgeQuestion(@RequestParam("questionSubmitId") long questionSubmitId);
}
