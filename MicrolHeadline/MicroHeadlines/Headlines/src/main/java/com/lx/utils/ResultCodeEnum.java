package com.lx.utils;

/**
 * 统一返回结果状态信息类
 *
 */
public enum ResultCodeEnum {

    SUCCESS(200,"success"),
    USERNAME_NULL(201,"username is not null"),
    REGISTER_FAIL(202,"注册失败"),
    ID_IS_NULL(203,"id为空"),
    ACCESS_LIMIT(204,"访问频繁，稍后再试！"),
    LOGIN_ERROR(501,"username or password Error"),
    NOTLOGIN(504,"notLogin"),
    USERNAME_USED(505,"userNameUsed"),
    PUBLISH_ERROR(506,"发布失败");


    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
