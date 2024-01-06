package com.lx.lanojcodesandbox.service;


import com.lx.lanojcodesandbox.model.ExecuteCodeRequest;
import com.lx.lanojcodesandbox.model.ExecuteCodeResponse;

public interface CodeSandBox {
   /*
   * 执行代码
   * */
   ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
