package com.lx.backendUserService.controller.inner;

import com.lx.backendModel.model.entity.User;
import com.lx.backendServiceClient.service.UserFeignClient;
import com.lx.backendUserService.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
/*
* 该服务器内部调用，不是给前端的
*
* */
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;
    /*
     * 根据id获取用户
     * */
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId){
        return userService.getById(userId);
    }

    /*
     * 根据id获取用户列表
     * */
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList){
        return userService.listByIds(idList);
    }
}
