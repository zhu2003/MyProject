package com.lx.handler;

import com.lx.exception.AccessLimitException;
import com.lx.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class HandlerException {
    @ExceptionHandler(Exception.class)
    public Result handle(Throwable e){
        log.info(e.getMessage());
        return Result.build(null, 507,"系统错误");
    }
    @ExceptionHandler(AccessLimitException.class)
    public Result AccessHandle(AccessLimitException e){
        log.error(e.getMessage());
        return Result.build(null,e.getCode(),e.getMessage());
    }
}
