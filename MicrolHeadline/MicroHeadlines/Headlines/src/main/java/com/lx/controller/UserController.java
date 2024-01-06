package com.lx.controller;

import com.lx.entity.User;
import com.lx.service.UserService;
import com.lx.service.impl.UserServiceImpl;
import com.lx.utils.JwtHelper;
import com.lx.utils.Result;
import com.lx.utils.ResultCodeEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;
    /*
    * 用户登录接口
    * */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if(user.getUsername()==null || user.getUserPwd()==null)
            return Result.build(null, ResultCodeEnum.LOGIN_ERROR);
        Result r = userService.login(user);
        return r;
    }
    /*
    * 根据token获取用户信息
    * */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result r = userService.getUserInfo(token);
        return r;
    }
    /*
     * 注册用户名检查
     * */
    @PostMapping("/checkUserName")
    public Result checkUserName(String username){
        if(username==null || !StringUtils.hasLength(username)){
            return Result.build(null,ResultCodeEnum.USERNAME_NULL);
        }
        Result r = userService.checkUserName(username);
        return r;
    }
    /*
    * 用户注册功能
    * */
    @PostMapping("/regist")
    public Result register(@RequestBody User user){
        Result r = userService.regist(user);
        return r;
    }
    /*
    *登录验证和保护
    * */
    @GetMapping("/checkLogin")
    public Result checkLogin(@RequestHeader String token){
        if(StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)){
            //没有传或者过期 未登录
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}
