package com.lx.Vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TiebaVo {
    private Long tid;
    private String headImageUrl; //TODO 查询用户表
    private String nickName;    //TODO 查询用户表
    private LocalDateTime publishTime;
    private Integer likes;
    private Integer views;
    private String title;
    private String content;
    private Boolean isLike;
}
