package com.lx.backendJudgeService.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.lx.backendCommon.common.ErrorCode;
import com.lx.backendCommon.exception.BusinessException;
import com.lx.backendJudgeService.judge.codesandbox.CodeSandBox;
import com.lx.backendModel.model.codesandbox.ExecuteCodeRequest;
import com.lx.backendModel.model.codesandbox.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/*
* 远程代码沙箱（实际调用接口）
* */
public class RemoteSandBoxImpl implements CodeSandBox {
    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://192.168.50.133:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if(StringUtils.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"executeCode remoteSandbox error,message="+responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
