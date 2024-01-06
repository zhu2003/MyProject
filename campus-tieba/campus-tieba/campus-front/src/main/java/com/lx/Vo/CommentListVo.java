package com.lx.Vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentListVo {
    private List<CommentVo> commentList;
    private List<CommentVo> myCommentList;
    private List<CommentVo> rows;
    private Integer total;
}
