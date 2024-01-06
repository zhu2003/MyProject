package com.lx.controller;

import com.lx.entity.Headline;
import com.lx.service.HeadlineService;
import com.lx.utils.JwtHelper;
import com.lx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/headline")
public class HeadlineController {
    @Autowired
    private HeadlineService headlineService;
    @Autowired
    private JwtHelper jwtHelper;
    /*
    * 头条发布实现
    * */
    @PostMapping("/publish")
    public Result publish(@RequestBody Headline headline, @RequestHeader String token){
        //获取用户id
        Long userId = jwtHelper.getUserId(token);
        headline.setPublisher(userId.intValue());
        Result r = headlineService.publish(headline);
        return r;
    }
    /*
    * 根据新闻id查询新闻
    * */
    @PostMapping("/findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Result r = headlineService.findHeadlineByHid(hid);
        return r;
    }
    /*
    * 头条修改
    * */
    @PostMapping("/update")
    public Result update(@RequestBody Headline headline){
        Result r = headlineService.updateHeadline(headline);
        return r;
    }
    /*
    * 根据id删除文章
    * */
    @PostMapping("/removeByHid")
    public Result removeByHid(Integer hid){
        Result r = headlineService.removeByHid(hid);
        return r;
    }
}
