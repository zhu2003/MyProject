package com.lx.controller;

import com.lx.utils.CheckCodeUtil;
import com.lx.utils.RedisCache;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CodeController {
    @Autowired
    private RedisCache redisCache;
    private final String PREFIX = "Login:";

    @GetMapping("/CheckCode")
    public void getCode(HttpServletResponse response) throws Exception{
        ServletOutputStream os = response.getOutputStream();
        String code = CheckCodeUtil.outputVerifyImage(80, 40, os, 4);
        log.info("验证码:{}",code);
        redisCache.setValue(PREFIX+"CheckCode",code,60l);
    }
}
