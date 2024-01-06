package com.lx.filter;

import com.alibaba.fastjson.JSON;
import com.lx.common.Result;
import com.lx.common.StatusCode;
import com.lx.entity.User;
import com.lx.exception.CustomExcetption;
import com.lx.utils.JwtUtil;
import com.lx.utils.RedisCache;
import com.lx.utils.ServletResponse;
import com.lx.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TokenUtil tokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取访问路径
        String pathUri = request.getRequestURI();
        //定义不需要拦截的路径
        String[] pathArray = new String[]{
                "/user/login",
                "/CheckCode",
                "/tieba/getTiebas",
                "/user/register",
                "/tieba/getHotTieba",
                "/static"
        };
        //不需要处理
        if(checkPath(pathArray,pathUri)){
            filterChain.doFilter(request,response);
            return;
        }
        //获取请求头中的token
        String token = request.getHeader("token");
        //判断token是否为空
        if(!StringUtils.hasText(token)){
            ServletResponse.print(response,new Result<>(StatusCode.UNLOGIN.getCode(), StatusCode.UNLOGIN.getMsg()));
            return;
        }
        //放行
        User user = tokenUtil.getLoginUser(token, response);
        if (user != null) {
            filterChain.doFilter(request,response);
        }
    }
    /*
    * 检查路径是否需要拦截
    * */
    public Boolean checkPath(String[] pathArray,String pathUri){
        for (String path : pathArray) {
            if(path.equals(pathUri) || path.contains("/static")){
                return true;
            }
        }
        return false;
    }
}
