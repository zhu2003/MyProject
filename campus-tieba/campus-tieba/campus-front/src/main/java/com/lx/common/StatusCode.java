package com.lx.common;

public enum StatusCode {
    SUCCESS(2000,"响应成功"),
    ERROR(2001,"响应失败"),
    UNAUTHORIZED(2002,"认证失败"),
    FORBIDDEN(2003,"权限不足"),
    SYSTEM_ERROR(500,"系统异常"),
    CUSTOM_ERROR(405,"用户错误"),
    UNLOGIN(406,"未登录"),
    USERORPASS_ERROR(407,"用户名或密码错误！"),
    CHECKCODE_TIMEOUT(408,"验证码过期"),
    CHECKCODE_ERROR(408,"验证码错误"),
    TOKEN_ILLEGAL(409,"令牌非法"),
    LOGIN_TIMEOUT(410,"登录过期"),
    USERORPASS_FORMAT_ERROR(410,"格式错误"),
    REGISTER_FAILED(411,"注册失败"),
    PUBLISH_SUCCESS(412,"发布成功"),
    PUBLISH_ERROR(413,"发布失败！"),
    LIKE_REPEAT(414,"已点赞"),
    TIEBA_DELETE(415,"贴吧删除成功");


    private Integer code;
    private String msg;
    private StatusCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
