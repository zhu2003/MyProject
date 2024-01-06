package com.lx.lanoj.judge.strategy;

import com.lx.lanoj.judge.codesandbox.model.JudgeInfo;

public interface JudgeStrategy {
    /*
    * 执行判题
    * */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
