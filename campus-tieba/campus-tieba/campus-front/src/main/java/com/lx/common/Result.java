package com.lx.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class Result<T>{
    private Integer code;
    private String msg;
    private Object data;
    private static final HashMap<Object,Object> map = new HashMap<>();
    public Result(){};

    public Result(Integer code ,String msg){
        this.code = code;
        this.msg = msg;
        this.data = map;
    }
    public static <T> Result<T> ok(){
        Result<T> result = new Result<>();
        result.code = StatusCode.SUCCESS.getCode();
        result.msg = StatusCode.SUCCESS.getMsg();
        result.data = map;
        return result;
    }
    /*响应正确*/
    public static <T> Result<T> ok(Object data){
        Result<T> result = new Result<>();
        result.code = StatusCode.SUCCESS.getCode();
        result.msg = StatusCode.SUCCESS.getMsg();
        result.data = data;
        return result;
    }

    /*
    * 响应错误
    * */
    public static <T> Result<T> error(){
        Result<T> result = new Result<>();
        result.code = StatusCode.ERROR.getCode();
        result.msg = StatusCode.ERROR.getMsg();
        result.data = map;
        return result;
    }
    public static <T> Result<T> error(StatusCode code){
        Result<T> result = new Result<>();
        result.code = code.getCode();
        result.msg = code.getMsg();
        result.data = map;
        return result;
    }
}
