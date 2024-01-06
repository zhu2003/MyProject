package com.lx.dto;


import lombok.Data;

@Data
public class HeadlineDto {
    //头条id
    private Integer hid;
    //头条标题
    private String title;
    //头条新闻内容
    private String article;
    //头条类型id
    private Integer type;
    //新闻所属类别
    private String typeName;
    //头条发布用户id
    private Integer publisher;
    //头条浏览量
    private Integer pageViews;
    //发布时间已过小时数
    private Integer pastHours;
    //新闻作者
    private String author;



}
