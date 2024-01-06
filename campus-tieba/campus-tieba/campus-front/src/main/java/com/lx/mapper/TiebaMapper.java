package com.lx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.lx.entity.Tieba;

/**
 * (Tieba)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-18 11:13:30
 */
@Mapper
public interface TiebaMapper extends BaseMapper<Tieba> {

}

