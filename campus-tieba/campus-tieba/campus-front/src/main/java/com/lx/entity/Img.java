package com.lx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("campus_img")
public class Img {
    @TableId
    private Integer id;
    private String name;
    private byte[] binData;
}
