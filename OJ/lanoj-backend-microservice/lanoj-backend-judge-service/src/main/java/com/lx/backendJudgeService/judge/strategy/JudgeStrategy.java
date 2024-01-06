package com.lx.backendJudgeService.judge.strategy;


import com.lx.backendModel.model.codesandbox.JudgeInfo;

public interface JudgeStrategy {
    /*
    * 执行判题
    * */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
