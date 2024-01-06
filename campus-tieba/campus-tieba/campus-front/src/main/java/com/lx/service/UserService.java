package com.lx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.Vo.LoginInfo;
import com.lx.common.Result;
import com.lx.entity.User;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-10-11 14:43:35
 */
public interface UserService extends IService<User> {
    /*
     * 登录接口
     * */
    Result login(LoginInfo loginInfo);
    /*
    * 获取用户信息
    * */
    Result getUserInfo(String token);
    /*
    * 注册用户
    * */
    Result register(User user);
    /*
    * 退出登录
    * */
    Result logout(String token);
    /*
     * 修改个人信息
     * */
    Result modifyUserInfo(User user);
    /*
     * 获取个人空间用户数据
     * */
    Result getSpaceUserInfo();
}

