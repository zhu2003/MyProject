package com.lx.lanoj.judge.service;

import com.lx.lanoj.model.entity.QuestionSubmit;
import com.lx.lanoj.model.vo.QuestionSubmitVO;

/*
* 判题服务
* */
public interface JudgeService {
    /*
    * 判题
    * */
    QuestionSubmit doJudge(long questionSubmitId);
}
