package com.lx.exception;

import com.lx.common.StatusCode;

public class CustomExcetption extends RuntimeException{
    private String msg;
    private Integer code;
    public String getMsg(){
        return this.msg;
    }
    public Integer getCode(){
        return this.code;
    }
    public CustomExcetption(StatusCode statusCode){
        super(statusCode.getMsg());
        this.msg = statusCode.getMsg();
        this.code = statusCode.getCode();
    }

}
