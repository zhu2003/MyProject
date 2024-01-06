package com.lx.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
/**
 * Comment 实体类
 * @author lx
 * @since 2023-10-26 15:03:21
 */

@Data
public class Comment {
    @TableId
    private Integer id;
    
    private Integer tiebaId;
    
    private Integer rootId;
    
    private String content;
    
    private String createBy;
    
    private String toCommentUser;
    
    private Integer toCommentId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    private Integer deleted;




}

