package com.lx.backendJudgeService.judge.codesandbox.impl;


import com.lx.backendJudgeService.judge.codesandbox.CodeSandBox;
import com.lx.backendModel.model.codesandbox.ExecuteCodeRequest;
import com.lx.backendModel.model.codesandbox.ExecuteCodeResponse;
import com.lx.backendModel.model.codesandbox.JudgeInfo;
import com.lx.backendModel.model.enums.JudgeInfoMessageEnum;
import com.lx.backendModel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/*
* 示例代码沙箱
* */
public class ExampleSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse codeResponse = new ExecuteCodeResponse();
        codeResponse.setOutputList(inputList);
        codeResponse.setMessage("调试执行成功");
        codeResponse.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);
        codeResponse.setJudgeInfo(judgeInfo);
        return codeResponse;
    }
}
