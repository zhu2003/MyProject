package com.lx.backendJudgeService.judge.codesandbox;


import com.lx.backendJudgeService.judge.codesandbox.impl.ExampleSandBoxImpl;
import com.lx.backendJudgeService.judge.codesandbox.impl.RemoteSandBoxImpl;
import com.lx.backendJudgeService.judge.codesandbox.impl.ThirdPartSandBoxImpl;

/*
* 代码沙箱工厂模式：根据名称返回指定的沙箱
*
* */
public class CodeSandBoxFactory {

    public static CodeSandBox getCodeSandBox(String name) {
        if (name == null) {
            return null;
        }
        if (name.equals("remote")) {
            return new RemoteSandBoxImpl();
        } else if (name.equals("example")) {
            return new ExampleSandBoxImpl();
        } else if (name.equals("third_part")) {
            return new ThirdPartSandBoxImpl();
        } else {
            return null;
        }
    }
}
