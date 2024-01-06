package com.lx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.entity.User;
import com.lx.utils.Result;

public interface UserService extends IService<User> {
    /*
    * 登录接口
    * */
    Result login(User user);
    /*
    * 获取用户数据
    * */
    Result getUserInfo(String token);
    /*
    * 注册用户名检查
    * */
    Result checkUserName(String username);
    /*
     * 用户注册功能
     * */
    Result regist(User user);
}
