package com.lx.Vo;

import lombok.Data;

import java.util.List;

/*

* */
@Data
public class PageInfoVo {
    private List pageData;
    private Long pageNum;
    private Long pageSize;
    private Long totalPage;
    private Long totalSize;

}
