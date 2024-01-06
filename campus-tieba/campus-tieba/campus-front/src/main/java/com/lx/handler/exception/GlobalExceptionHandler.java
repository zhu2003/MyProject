package com.lx.handler.exception;

import com.lx.common.Result;
import com.lx.common.StatusCode;
import com.lx.exception.CustomExcetption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    /*用户自定义异常*/
    @ExceptionHandler(CustomExcetption.class)
    public Result customException(CustomExcetption e){
        log.info("出现了异常{}",e.getMessage());
        return new Result(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        log.info("出现了异常{}",e.getMessage());
        return new Result(StatusCode.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
