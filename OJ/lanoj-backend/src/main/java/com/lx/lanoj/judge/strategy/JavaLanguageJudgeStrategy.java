package com.lx.lanoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.lx.lanoj.model.dto.question.JudgeCase;
import com.lx.lanoj.model.dto.question.JudgeConfig;
import com.lx.lanoj.judge.codesandbox.model.JudgeInfo;
import com.lx.lanoj.model.entity.Question;
import com.lx.lanoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.Optional;

/*
* Java判题策略
* */
public class JavaLanguageJudgeStrategy implements JudgeStrategy{
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setMemory(memory);
        if(outputList.size()!=inputList.size()){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judge = judgeCaseList.get(i);
            if(!judge.getOutput().equals(outputList.get(i))){
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制
        String judgeConfig = question.getJudgeConfig();
        JudgeConfig config = JSONUtil.toBean(judgeConfig, JudgeConfig.class);
        Long needTimeLimit = config.getTimeLimit();
        Long needMemoryLimit = config.getMemoryLimit();
        //java本身要额外执行10s
        if(time-10>needTimeLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if(memory>needMemoryLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
