package com.lx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
/**
 * User 实体类
 * @author lx
 * @since 2023-08-28 15:11:54
 */

@Data
public class User {

    //用户id
    @TableId
    private Integer uid;
    //用户登录名
    private String username;
    //用户登录密码密文
    private String userPwd;
    //用户昵称
    private String nickName;
    //乐观锁
    @Version
    private Integer version;
    //头条是否被删除 1 删除  0 未删除
    @TableLogic
    private Integer isDeleted;




}

