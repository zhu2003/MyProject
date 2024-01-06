package com.lx.controller;

import com.lx.annotation.AccessLimit;
import com.lx.entity.PageInfo;
import com.lx.service.HeadlineService;
import com.lx.service.TypeService;
import com.lx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portal")
public class HomePageController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private HeadlineService headlineService;
    /*
    * 获取所有分类
    * */
    @AccessLimit
    @GetMapping("/findAllTypes")
    public Result findAllTypes(){
        Result r = typeService.findAllTypes();
        return r;
    }
    /*
    * 分页查询首页头条信息
    * */
    @AccessLimit
    @PostMapping("/findNewsPage")
    public Result findNewsPage(@RequestBody PageInfo pageInfo){
        Result r = headlineService.findNewsPage(pageInfo);
        return r;
    }
    /*
    * 查询头条详细内容
    * */
    @AccessLimit
    @PostMapping("/showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result r = headlineService.showHeadlineDetail(hid);
        return r;
    }
}
