package com.lx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.lx.entity.Comment;

/**
 * (Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-26 15:03:21
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}

