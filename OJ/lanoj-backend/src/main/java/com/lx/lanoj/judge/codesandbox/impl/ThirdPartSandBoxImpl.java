package com.lx.lanoj.judge.codesandbox.impl;

import com.lx.lanoj.judge.codesandbox.CodeSandBox;
import com.lx.lanoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lx.lanoj.judge.codesandbox.model.ExecuteCodeResponse;

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
