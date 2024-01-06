package com.lx.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tieba 实体类
 * @author lx
 * @since 2023-10-18 11:13:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tieba {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long tid;

    private String title;
    
    private Integer views;
    
    private Integer likes;
    
    private String content;
    
    private String likeUsers;

    private String createBy;
    
    private LocalDateTime publishTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private Integer deleted;




}

