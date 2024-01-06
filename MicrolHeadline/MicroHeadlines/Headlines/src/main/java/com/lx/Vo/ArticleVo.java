package com.lx.Vo;

import lombok.Data;

/*
*           "hid":"1",
            "title":"马斯克宣布",
            "article":"... ... ",
            "type":"2"
* */
@Data
public class ArticleVo {
    private Integer hid;
    private String title;
    private String article;
    private Integer type;
}
