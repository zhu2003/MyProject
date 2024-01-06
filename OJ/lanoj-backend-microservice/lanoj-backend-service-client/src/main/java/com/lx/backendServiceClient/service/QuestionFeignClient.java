package com.lx.backendServiceClient.service;


import com.lx.backendModel.model.entity.Question;
import com.lx.backendModel.model.entity.QuestionSubmit;
import com.lx.backendServiceClient.fallback.QuestionClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @author 蓝朽
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-12-27 08:55:31
*/
@FeignClient(name = "lanoj-backend-question-service",path = "/api/question/inner",fallbackFactory = QuestionClientFallbackFactory.class)
public interface QuestionFeignClient{

    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

}
