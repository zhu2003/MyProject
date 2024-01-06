package com.lx.Vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class CommentVo {
    private Integer id;
    private String userId;
    private Integer tid;
    private String imgUrl;
    private String nickName;
    private Date commentTime;
    //文章标题
    private String title;
    private String content;
    private String createBy;
    private Integer cid;
    private String toCommentUserName;
    private List<CommentVo> children;
}
