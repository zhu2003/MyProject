package com.lx.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
/**
 * Headline 实体类
 * @author lx
 * @since 2023-08-28 15:12:51
 */

@Data
public class Headline {
    //头条id
    @TableId
    private Integer hid;
    //头条标题
    private String title;
    //头条新闻内容
    private String article;
    //头条类型id
    private Integer type;
    //头条发布用户id
    private Integer publisher;
    //头条浏览量
    private Integer pageViews;
    //头条发布时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //头条最后的修改时间
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //乐观锁
    @Version
    private Integer version;
    //头条是否被删除 1 删除  0 未删除
    @TableLogic
    private Integer isDeleted;




}

