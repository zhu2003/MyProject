package com.lx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.entity.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImgMapper extends BaseMapper<Img> {
    void insertImg(Img img);
}
