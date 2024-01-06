package com.lx.backendJudgeService.judge.codesandbox;


import com.lx.backendModel.model.codesandbox.ExecuteCodeRequest;
import com.lx.backendModel.model.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
/*
* 静态代理
* */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox{
    private final CodeSandBox codeSandBox;
    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱信息:"+executeCodeRequest.toString());
        ExecuteCodeResponse codeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱信息:"+codeResponse.toString());
        return codeResponse;
    }
}
