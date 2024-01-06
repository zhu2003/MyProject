package com.lx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
/**
 * Type 实体类
 * @author lx
 * @since 2023-08-28 15:12:51
 */

@Data
public class Type {
    //新闻类型id
    @TableId
    private Integer tid;
    //新闻类型描述
    private String tname;
    //乐观锁
    @Version
    private Integer version;
    //头条是否被删除 1 删除  0 未删除
    @TableLogic
    private Integer isDeleted;

}

