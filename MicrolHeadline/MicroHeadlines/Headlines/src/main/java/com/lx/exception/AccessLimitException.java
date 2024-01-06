package com.lx.exception;

import com.lx.utils.ResultCodeEnum;

public class AccessLimitException extends RuntimeException{
    private Integer code;
    private String message;
    public Integer getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }
    public AccessLimitException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
