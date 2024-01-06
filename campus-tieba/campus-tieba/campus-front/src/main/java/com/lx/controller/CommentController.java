package com.lx.controller;



import com.lx.common.Result;
import com.lx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * (Comment)表控制层
 *
 * @author makejava
 * @since 2023-10-26 15:03:21
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    /**
     * 服务对象
     */
    @Autowired
    private CommentService commentService;
    /*
    * 获取空间列表评论
    * */
    @GetMapping
    public Result getCommentById(){
        return commentService.getCommentById();
    }
   /*
   * 获取贴吧评论列表
   * */
    @GetMapping("/commentList")
    public Result commentList(Integer pageNum,Integer pageSize,Long tid){
        return commentService.commentList(pageNum,pageSize,tid);
    }
}

