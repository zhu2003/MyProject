package com.lx.utils;

import com.lx.common.Result;
import com.lx.common.StatusCode;
import com.lx.entity.User;
import com.lx.exception.CustomExcetption;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
@Component
public class TokenUtil {
    @Autowired
    private RedisCache redisCache;
    private final String PREFIX = "user:";
    public User getLoginUser(String token){
        //判断token是否为空
        if (!StringUtils.hasText(token)){
            throw new CustomExcetption(StatusCode.UNLOGIN);
        }
        //解析token
        Claims claims=null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            throw new CustomExcetption(StatusCode.TOKEN_ILLEGAL);
        }
        String id = claims.getSubject();
        //从redis中获取数据
        User user = (User)redisCache.getValue(PREFIX + id);
        //判断登录用户是否过期
        if (Objects.isNull(user)){
            throw new CustomExcetption(StatusCode.LOGIN_TIMEOUT);
        }
        return user;
    }
    public User getLoginUser(String token, HttpServletResponse response){
        //解析token
        Claims claims=null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            ServletResponse.print(response,new Result<>(StatusCode.TOKEN_ILLEGAL.getCode(), StatusCode.TOKEN_ILLEGAL.getMsg()));
            return null;
        }
        String id = claims.getSubject();
        //从redis中获取数据
        User user = (User)redisCache.getValue(PREFIX + id);
        //判断登录用户是否过期
        if (Objects.isNull(user)){
            ServletResponse.print(response,new Result<>(StatusCode.LOGIN_TIMEOUT.getCode(), StatusCode.LOGIN_TIMEOUT.getMsg()));
            return null;
        }
        return user;
    }
}
