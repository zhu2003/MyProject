package com.lx.utils;

import com.alibaba.fastjson.JSON;
import com.lx.common.Result;
import com.lx.common.StatusCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ServletResponse {
    public static void print(HttpServletResponse response,Result<Object> result){
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try{
            response.getWriter().print(JSON.toJSONString(result));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
