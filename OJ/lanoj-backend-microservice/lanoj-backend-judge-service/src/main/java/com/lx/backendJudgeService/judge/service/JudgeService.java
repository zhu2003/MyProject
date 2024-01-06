package com.lx.backendJudgeService.judge.service;


import com.lx.backendModel.model.entity.QuestionSubmit;

/*
* 判题服务
* */
public interface JudgeService {
    /*
    * 判题
    * */
    QuestionSubmit doJudge(long questionSubmitId);
}
