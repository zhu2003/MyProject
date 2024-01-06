package com.lx.controller;

import com.lx.Vo.LoginInfo;
import com.lx.common.Result;
import com.lx.entity.User;
import com.lx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    /*
    * 登录接口
    * */
    @PostMapping("/login")
    public Result login(@RequestBody LoginInfo loginInfo){
        return userService.login(loginInfo);
    }
    /*
    * 获取用户信息
    * */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader("token") String token){
        return userService.getUserInfo(token);
    }
    /*
    * 获取个人空间用户数据
    * */
    @GetMapping
    public Result getSpaceUserInfo(){
        return userService.getSpaceUserInfo();
    }
    /*
    * 用户注册
    * */
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }
    /*
    * 退出登录
    * */
    @GetMapping("/logout")
    public Result logout(@RequestHeader("token") String token){
        return userService.logout(token);
    }
    /*
    * 修改个人信息
    * */
    @PostMapping
    public Result modifyUserInfo(@RequestBody User user){
        return userService.modifyUserInfo(user);
    }

}
