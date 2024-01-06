package com.lx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.common.Result;
import com.lx.entity.Comment;

/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2023-10-26 15:03:22
 */
public interface CommentService extends IService<Comment> {
    /*
     * 获取空间列表评论
     * */
    Result getCommentById();
    /*
     * 获取贴吧评论列表
     * */
    Result commentList(Integer pageNum, Integer pageSize, Long tid);
}

