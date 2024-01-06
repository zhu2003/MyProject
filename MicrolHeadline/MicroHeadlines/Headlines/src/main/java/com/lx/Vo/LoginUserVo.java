package com.lx.Vo;

import lombok.Data;

@Data
public class LoginUserVo {
    private Integer uid;
    //用户登录名
    private String username;
    //用户昵称
    private String nickName;
}
