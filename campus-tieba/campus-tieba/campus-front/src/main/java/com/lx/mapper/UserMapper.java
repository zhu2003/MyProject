package com.lx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.lx.entity.User;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-11 14:43:34
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

