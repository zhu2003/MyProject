package com.lx.Vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class TypeVo {
    //新闻类型id
    @TableId
    private Integer tid;
    //新闻类型描述
    private String tname;
}