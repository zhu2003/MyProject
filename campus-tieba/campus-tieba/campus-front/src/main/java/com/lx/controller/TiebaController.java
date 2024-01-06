package com.lx.controller;



import com.fasterxml.jackson.databind.node.IntNode;
import com.lx.common.Result;
import com.lx.entity.Search;
import com.lx.entity.Tieba;
import com.lx.service.TiebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * (Tieba)表控制层
 *
 * @author makejava
 * @since 2023-10-18 11:13:30
 */
@RestController
@RequestMapping("/tieba")
public class TiebaController{
    /**
     * 服务对象
     */
    @Autowired
    private TiebaService tiebaService;

    /*
    * 发布或更新贴吧
    * */
    @PutMapping
    public Result publish(@RequestBody Tieba tieba,@RequestHeader("token") String token){
        return tiebaService.publish(tieba,token);
    }
    /*
    * 获取贴吧列表
    * */
    @GetMapping("/getTiebas")
    public Result getTiebas(){
        return tiebaService.getTiebas();
    }
    /*
    * 获取热门贴吧
    * */
    @GetMapping("/getHotTieba")
    public Result getHotTieba(){
        return tiebaService.getHotTieba();
    }
    /*
    * 获取贴吧详情
    * */
    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Long tid){
        return tiebaService.getDetail(tid);
    }
   /*
   * 点赞接口
   * */
    @GetMapping("/like/{tid}")
    public Result addLike(@PathVariable Long tid){
        return tiebaService.addLike(tid);
    }
    /*
    *取消点赞
    * */
    @DeleteMapping("/like/{tid}")
    public Result cancelLike(@PathVariable Long tid){
        return tiebaService.cancelLike(tid);
    }

    /*
    * 增加浏览量
    * */
    @GetMapping("/view/{tid}")
    public Result addView(@PathVariable Long tid){
        return tiebaService.addView(tid);
    }
    /*
    * 根据关键词搜索贴吧
    * */
    @GetMapping("/search/{keyWord}")
    public Result search(@PathVariable String keyWord){
        return tiebaService.search(keyWord);
    }
    /*
    * 获取个人空间的贴吧列表
    * */
    @GetMapping("/spaceTiebaList")
    public Result spaceTiebaList(){
        return tiebaService.spaceTiebaList();
    }
    /*
    * 删除贴吧
    * */
    @DeleteMapping("/{tid}")
    public Result deleteTieba(@PathVariable Long tid){
        return tiebaService.deleteTieba(tid);
    }
    /*
    * 个人空间贴吧搜索
    * */
    @PostMapping("/spaceTiebaSearch")
    public Result spaceTiebaSearch(@RequestBody Search search){
        return tiebaService.spaceTiebaSearch(search);
    }

}

