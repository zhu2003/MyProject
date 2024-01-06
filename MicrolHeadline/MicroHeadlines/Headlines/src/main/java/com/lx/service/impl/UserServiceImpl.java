package com.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.Vo.LoginUserVo;
import com.lx.entity.User;
import com.lx.mapper.UserMapper;
import com.lx.service.UserService;
import com.lx.utils.JwtHelper;
import com.lx.utils.MD5Util;
import com.lx.utils.Result;
import com.lx.utils.ResultCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtHelper jwtHelper;
    /*
     * 登录接口
     * */
    @Override
    public Result login(User user)   {
        //将密码用MD5加密
        String password = MD5Util.encrypt(user.getUserPwd());
        //构造查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername())
                .eq(User::getUserPwd,password);
        //查询用户信息
        User selectOne = userMapper.selectOne(wrapper);
        //判断是否为空
        if(selectOne==null) throw new RuntimeException();
        //生成token并返回token
        String token = jwtHelper.createToken(selectOne.getUid().longValue());
        HashMap<String, String> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }
    /*
    * 获取用户数据
    * */

    @Override
    public Result getUserInfo(String token) {
        //判断token是否过期
        if(jwtHelper.isExpiration(token)) return Result.build(null, ResultCodeEnum.NOTLOGIN);
        //获取用户id
        Long id = jwtHelper.getUserId(token);
        //根据id查询用户
        User user = userMapper.selectById(id);
        if(user==null) return Result.build(null,ResultCodeEnum.NOTLOGIN);
        System.out.println(user);
        //封装数据
        Map map = new HashMap();
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(user,loginUserVo);
        map.put("loginUser",loginUserVo);
        return Result.ok(map);
    }
    /*
    * 注册用户名检查
    * */

    @Override
    public Result checkUserName(String username) {
        //构造条件
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername,username);
        //查询
        User user = userMapper.selectOne(lqw);
        if(user!=null) return Result.build(null,ResultCodeEnum.USERNAME_USED);
        return Result.ok(null);
    }
    /*
     * 用户注册功能
     * */

    @Override
    public Result regist(User user) {
        //判断是否有空值
        if(user.getUsername()=="" || user.getUserPwd()=="" || user.getNickName() == ""){
            return Result.build(null,ResultCodeEnum.REGISTER_FAIL);
        }
        //密码加密
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        userMapper.insert(user);
        return Result.ok(null);
    }
}
