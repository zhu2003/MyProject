package com.lx.lanojcodesandbox.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.lx.lanojcodesandbox.model.ExecuteMessage;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ProcessUtils {
    /*
    * 执行进程获取信息
    * */
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess,String opName) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try{
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            //等待执行完毕
            int exitValue = runProcess.waitFor();
            executeMessage.setExitCode(exitValue);
            //判断是否执行成功
            if(exitValue==0){
                System.out.println(opName+"成功");
                //获取控制台输出信息
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder complieOutputStringBuilder = new StringBuilder();
                String complieOutputLine;
                //循环读取输出信息
                while ((complieOutputLine=bufferedReader.readLine())!=null){
                    complieOutputStringBuilder.append(complieOutputLine).append("\n");
                }
                executeMessage.setMessage(complieOutputStringBuilder.toString());
            }else{
                System.out.println(opName+"失败，错误码:"+exitValue);
                //获取控制台输出信息
                //String output = FileUtil.readString(runProcess.getInputStream().toString(), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder complieOutputStringBuilder = new StringBuilder();
                String complieOutputLine;
                //循环读取输出信息
                while ((complieOutputLine=bufferedReader.readLine())!=null){
                    complieOutputStringBuilder.append(complieOutputLine).append("\n");
                }
                executeMessage.setMessage(complieOutputStringBuilder.toString());
                //获取控制台输出信息
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                StringBuilder errorComplieOutputStringBuilder = new StringBuilder();
                String errorComplieOutputLine;
                //循环读取输出信息
                while ((errorComplieOutputLine=errorBufferedReader.readLine())!=null){
                    errorComplieOutputStringBuilder.append(errorComplieOutputLine).append("\n");
                }
                executeMessage.setErrorMessage(errorComplieOutputStringBuilder.toString());
            }
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getTotalTimeMillis());
        }catch (Exception e){
            e.printStackTrace();
        }
        return executeMessage;
    }

    //执行交互式进程
    public static ExecuteMessage runInteractiveProcessAndGetMessage(Process runProcess,String opName,String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try{
            //向控制台输入程序
            OutputStream outputStream = runProcess.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] s = args.split(" ");
            String join = StrUtil.join("\n", s) + "\n";
            outputStreamWriter.write(join);
            //相当于按了回车
            outputStreamWriter.flush();
            InputStream inputStream = runProcess.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder complieOutputStringBuilder = new StringBuilder();
            String complieOutputLine;
            //循环读取输出信息
            while ((complieOutputLine=bufferedReader.readLine())!=null){
                    complieOutputStringBuilder.append(complieOutputLine);
            }
            executeMessage.setMessage(complieOutputStringBuilder.toString());
            executeMessage.setExitCode(0);
            //资源释放
            outputStreamWriter.close();
            outputStream.close();
            inputStream.close();
            runProcess.destroy();
        }catch (Exception e){
            e.printStackTrace();
        }
        return executeMessage;
    }
}
