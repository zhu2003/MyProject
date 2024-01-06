package com.lx.lanoj.judge.codesandbox;

import com.lx.lanoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lx.lanoj.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandBox {
   /*
   * 执行代码
   * */
   ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
