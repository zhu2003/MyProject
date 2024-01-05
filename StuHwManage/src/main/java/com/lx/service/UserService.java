package com.lx.service;

import com.lx.pojo.PageBean;
import com.lx.pojo.User;
import com.lx.pojo.User_Cscore;

import java.util.List;

public interface UserService {
    //根据用户名和密码判断是否登录成功
    User selectByNameAndPasswd(String username, String password);
    //根据 用户名修改密码
    void updatePw(User user);
    //查询所有
    List<User> selectAll();
    /*
* 分页条件查询
CurrentPage:当前页
pageSize:每页条数
 */
    PageBean<User_Cscore> selectByPageByCondition(int CurrentPage,int pageSize,User_Cscore user);
    //更新操行分
    void updateScore(int id);
    //减少分数
    void reduceScore(int id);
    //修改用户信息
    void updateUser(User user);
    //重置状态
    void resetStatus();
    //改变状态
    void ChangeStatus(int id);

}
