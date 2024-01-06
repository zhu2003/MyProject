package com.lx.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
/**
 * User 实体类
 * @author lx
 * @since 2023-10-11 14:43:35
 */

@Data
public class User implements Serializable {
    //主键
    private String id;
    
    private String nickname;
    
    private String username;
    
    private String password;
    
    private String qqEmail;
    
    private String github;
    
    private String sign;
    
    private Integer tiebas;
    
    private Integer follows;
    
    private Integer fans;
    
    private String headImageUrl;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //逻辑删除字段
    @TableLogic
    private Integer deleted;


}

