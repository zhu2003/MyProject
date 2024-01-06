package com.lx.lanojcodesandbox.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import com.lx.lanojcodesandbox.model.ExecuteCodeRequest;
import com.lx.lanojcodesandbox.model.ExecuteCodeResponse;
import com.lx.lanojcodesandbox.model.ExecuteMessage;
import com.lx.lanojcodesandbox.model.JudgeInfo;
import com.lx.lanojcodesandbox.security.DefaultSecurityManager;
import com.lx.lanojcodesandbox.utils.ProcessUtils;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class JavaNativeCodeSandBox implements CodeSandBox{
    private static final String GlOBAL_CODE_DIR_NAME = "tempCode";
    private static final String GlOBAL_JAVA_CLASS_NAME = "Main.java";
    private static final long TIME_OUT = 5000L;

    public static void main(String[] args) {
        JavaNativeCodeSandBox javaNativeCodeSandBox = new JavaNativeCodeSandBox();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Arrays.asList("1 2","3 4"));
        String code = ResourceUtil.readStr("testCode/simpleCompute/Main.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandBox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        //System.setSecurityManager(new DefaultSecurityManager());
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir+ File.separator+ GlOBAL_CODE_DIR_NAME;
        if(!FileUtil.exist(globalCodePathName)){
            FileUtil.mkdir(globalCodePathName);
        }
        //用户的代码隔离存放
        String userCodeParentPath = globalCodePathName+File.separator+ UUID.randomUUID();
        String userCodePath = userCodeParentPath+File.separator+GlOBAL_JAVA_CLASS_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
        //编译代码
        String complieCmd = String.format("javac -encoding utf-8 %s",userCodeFile.getAbsolutePath());
        try{
            Process complieProcess = Runtime.getRuntime().exec(complieCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(complieProcess, "编译");
            System.out.println(executeMessage);
        }catch (Exception e){
            //文件清理
            delFile(userCodeFile.getParentFile());
            return getErrorResponse(e);
        }
        //执行代码
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String arg: inputList) {
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s",userCodeParentPath,arg);
            try{
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                //超时控制
                new Thread(() -> {
                    try {
                        Thread.sleep(TIME_OUT);
                        runProcess.destroy();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                //ExecuteMessage executeMessage = ProcessUtils.runInteractiveProcessAndGetMessage(runProcess, "运行",arg);
                System.out.println(executeMessage);
                executeMessageList.add(executeMessage);
            }catch (Exception e){
                //文件清理
                delFile(userCodeFile.getParentFile());
                return getErrorResponse(e);
            }
        }
        //收集整理输出结果
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        long maxTime = 0;
        for (ExecuteMessage executeMessage : executeMessageList) {
            String errorMessage = executeMessage.getErrorMessage();
            if(StrUtil.isNotBlank(errorMessage)){
                executeCodeResponse.setMessage(errorMessage);
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            if (time != null) {
                maxTime = Math.max(maxTime,time);
            }
        }
        if(outputList.size() == executeMessageList.size()){
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        //文件清理
        delFile(userCodeFile.getParentFile());
        return executeCodeResponse;
    }
    /*
    *获取错误响应
    * */
    private ExecuteCodeResponse getErrorResponse(Throwable e){
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setOutputList(new ArrayList<>());
        response.setMessage(e.getMessage());
        //表示代码沙箱错误
        response.setStatus(2);
        response.setJudgeInfo(null);
        return response;
    }
    /*
    * 删除文件
    * */
    private void delFile(File file){
        if(file!=null){
            boolean del = FileUtil.del(file);
            System.out.println("删除"+(del?"成功":"失败"));
        }
    }
}
