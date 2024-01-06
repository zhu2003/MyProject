package com.lx.backendJudgeService.judge.codesandbox.impl;


import com.lx.backendJudgeService.judge.codesandbox.CodeSandBox;
import com.lx.backendModel.model.codesandbox.ExecuteCodeRequest;
import com.lx.backendModel.model.codesandbox.ExecuteCodeResponse;

/*
* 第三方代码沙箱（实际调用接口）
* */
public class ThirdPartSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
