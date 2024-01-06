package com.lx.backendJudgeService.judge.codesandbox;


import com.lx.backendModel.model.codesandbox.ExecuteCodeRequest;
import com.lx.backendModel.model.codesandbox.ExecuteCodeResponse;

public interface CodeSandBox {
   /*
   * 执行代码
   * */
   ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
