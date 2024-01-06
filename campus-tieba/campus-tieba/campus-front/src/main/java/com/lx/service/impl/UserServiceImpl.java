package com.lx.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.Vo.LoginInfo;
import com.lx.Vo.UserInfoVo;
import com.lx.Vo.UserVo;
import com.lx.common.Result;
import com.lx.common.StatusCode;
import com.lx.entity.User;
import com.lx.exception.CustomExcetption;
import com.lx.mapper.UserMapper;
import com.lx.service.UserService;
import com.lx.utils.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-10-11 14:43:36
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TokenUtil tokenUtil;
    private final String PREFIX = "user:";
    /*
    * 登录
    * */
    @Override
    public Result login(LoginInfo loginInfo) {
        if (loginInfo.getUsername().length()!=6 || loginInfo.getPassword().length()!=8) {
            throw new CustomExcetption(StatusCode.USERORPASS_ERROR);
        }
        String checkCode = loginInfo.getCheckCode().toLowerCase();
        String code = (String)redisCache.getValue("Login:CheckCode");
        if (code == null){
            throw new CustomExcetption(StatusCode.CHECKCODE_TIMEOUT);
        }else if (!code.toLowerCase().equals(checkCode)){
            throw new CustomExcetption(StatusCode.CHECKCODE_ERROR);
        }
        //构造查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //密码加密
        String password = PasswordEncode.encode(loginInfo.getPassword());
        wrapper.eq(User::getUsername,loginInfo.getUsername())
                .eq(User::getPassword,password);
        User user = userMapper.selectOne(wrapper);
        //判断是否为空
        if (user==null){
            throw new CustomExcetption(StatusCode.USERORPASS_ERROR);
        }
        //根据id生成token
        String id = user.getId().toString();
        String token = JwtUtil.createJWT(id);
        //缓存到redis中
        redisCache.setValue(PREFIX+id,user,60l*60);
        //封装数据返回
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }
    /*
    * 获取用户信息
    * */
    @Override
    public Result getUserInfo(String token) {
        //获取用户信息
        User user = tokenUtil.getLoginUser(token);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);
        //封装数据
        HashMap<String, UserInfoVo> map = new HashMap<>();
        map.put("userInfo",userInfoVo);
        return Result.ok(map);
    }
    /*
     * 注册用户
     * */
    @Override
    public Result register(User user) {
        //字段校验
        if (user.getUsername().length()!=6 || user.getPassword().length() != 8) {
            throw new CustomExcetption(StatusCode.USERORPASS_FORMAT_ERROR);
        }
        //判断用户是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User selectOne = userMapper.selectOne(wrapper);
        //不为空说明用户存在
        if (selectOne != null) {
            throw new CustomExcetption(StatusCode.REGISTER_FAILED);
        }
        //密码加密
        String password = PasswordEncode.encode(user.getPassword());
        user.setPassword(password);
        user.setNickname("小小西兰花");
        user.setGithub("未设置");
        user.setQqEmail("未设置");
        user.setSign("这个人很懒~");
        user.setHeadImageUrl("https://img1.baidu.com/it/u=2716101784,2013168604&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=566");

        //构造条件
        int count = userMapper.insert(user);
        if (count==0){
            throw new CustomExcetption(StatusCode.REGISTER_FAILED);
        }
        return Result.ok();
    }

    /*
     * 退出登录
     * */
    @Override
    public Result logout(String token) {
        //解析token
        User user = tokenUtil.getLoginUser(token);
        //从redis中删除用户信息
        redisCache.delValue(PREFIX+user.getId());
        return Result.ok();
    }
    /*
     * 修改个人信息
     * */
    @Override
    public Result modifyUserInfo(User user) {
        String token = ServletContext.getRequest().getHeader("token");
        User loginUser = tokenUtil.getLoginUser(token);
        user.setId(loginUser.getId());
        userMapper.updateById(user);
        User selectById = userMapper.selectById(loginUser.getId());
        //更新redis
        redisCache.setValue(PREFIX+loginUser.getId(),selectById,60l*60);
        return Result.ok();
    }
    /*
     * 获取个人空间用户数据
     * */
    @Override
    public Result getSpaceUserInfo() {
        String token = ServletContext.getRequest().getHeader("token");
        User user = tokenUtil.getLoginUser(token);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return Result.ok(userVo);
    }

}

