package com.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.Vo.CommentListVo;
import com.lx.Vo.CommentVo;
import com.lx.common.Result;
import com.lx.entity.Tieba;
import com.lx.entity.User;
import com.lx.mapper.CommentMapper;
import com.lx.entity.Comment;
import com.lx.mapper.TiebaMapper;
import com.lx.mapper.UserMapper;
import com.lx.service.CommentService;
import com.lx.utils.ServletContext;
import com.lx.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-10-26 15:03:22
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private TiebaMapper tiebaMapper;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserMapper userMapper;
    /*
     * 获取空间列表评论
     * */
    @Override
    public Result getCommentById() {
        //获取用户token
        String token = ServletContext.getRequest().getHeader("token");
        //解析token获取用户数据
        User isLogin = tokenUtil.getLoginUser(token);
        //构造查询条件
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Comment::getUpdateTime);
        List<Comment> commentList = commentMapper.selectList(wrapper);
        //查询对应的每一条评论的用户数据
        CommentListVo listVo = new CommentListVo();
        List<CommentVo> CommentVoList = commentList.stream().map(comment -> {
            //获取创建评论的用户id
            String createById = comment.getCreateBy();
            //根据文章id查询文章
            Tieba tieba = tiebaMapper.selectById(comment.getTiebaId());
            ///根据id查询用户信息
            User user = userMapper.selectById(createById);
            CommentVo commentVo = new CommentVo();
            commentVo.setUserId(user.getId());
            commentVo.setImgUrl(user.getHeadImageUrl());
            commentVo.setNickName(user.getNickname());
            commentVo.setTid(comment.getTiebaId());
            commentVo.setContent(comment.getContent());
            commentVo.setCommentTime(comment.getCreateTime());
            commentVo.setTitle(tieba.getTitle());
            return commentVo;
        }).collect(Collectors.toList());
        //我的所有评论
        wrapper.eq(Comment::getCreateBy,isLogin.getId());
        List<Comment> myCommentList = commentMapper.selectList(wrapper);
        List<CommentVo> myCommentVoList = myCommentList.stream().map(comment -> {
            //根据文章id查询文章
            Tieba tieba = tiebaMapper.selectById(comment.getTiebaId());
            CommentVo myCommentVo = new CommentVo();
            myCommentVo.setCommentTime(comment.getCreateTime());
            myCommentVo.setTid(comment.getTiebaId());
            myCommentVo.setUserId(isLogin.getId());
            myCommentVo.setContent(comment.getContent());
            myCommentVo.setNickName(isLogin.getNickname());
            myCommentVo.setImgUrl(isLogin.getHeadImageUrl());
            myCommentVo.setTitle(tieba.getTitle());
            return myCommentVo;
        }).collect(Collectors.toList());
        //封装数据
        listVo.setCommentList(CommentVoList);
        listVo.setMyCommentList(myCommentVoList);
        listVo.setTotal(CommentVoList.size()+myCommentList.size());
        //返回
        return Result.ok(listVo);
    }
    /*
     * 获取贴吧评论列表
     * */
    @Override
    public Result commentList(Integer pageNum, Integer pageSize, Long tid) {
        Page<Comment> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getTiebaId,tid).eq(Comment::getRootId,-1);
        commentMapper.selectPage(page,wrapper);
        List<Comment> records = page.getRecords();
        List<CommentVo> commentVoList = records.stream().map(item -> {
            CommentVo commentVo = new CommentVo();
            commentVo.setId(item.getId());
            commentVo.setCommentTime(item.getCreateTime());
            commentVo.setContent(item.getContent());
            User user = userMapper.selectById(item.getCreateBy());
            commentVo.setImgUrl(user.getHeadImageUrl());
            commentVo.setNickName(user.getNickname());
            //查询子评论
            LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Comment::getRootId,item.getId());
            List<Comment> childrenComments = commentMapper.selectList(lqw);
            if (childrenComments.size() ==0) {
                return commentVo;
            }else{
                List<CommentVo> childComment = childrenComments.stream().map(c -> {
                    CommentVo childCommentVo = new CommentVo();
                    childCommentVo.setCreateBy(c.getCreateBy());
                    childCommentVo.setCid(c.getId());
                    childCommentVo.setContent(c.getContent());
                    childCommentVo.setCommentTime(c.getCreateTime());
                    User cu = userMapper.selectById(item.getCreateBy());
                    childCommentVo.setImgUrl(cu.getHeadImageUrl());
                    childCommentVo.setNickName(cu.getNickname());
                    childCommentVo.setToCommentUserName(user.getNickname());
                    return childCommentVo;
                }).collect(Collectors.toList());
                commentVo.setChildren(childComment);
            }
            return commentVo;
        }).collect(Collectors.toList());
        CommentListVo listVo = new CommentListVo();
        listVo.setRows(commentVoList);
        listVo.setTotal((int) page.getTotal());
        return Result.ok(listVo);
    }
}

