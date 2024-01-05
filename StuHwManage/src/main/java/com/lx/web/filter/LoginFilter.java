package com.lx.web.filter;

import com.lx.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
* 登录验证的过滤器
* */
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强转req
        HttpServletRequest request = (HttpServletRequest) req;
        //判断访问路径是否和登录注册相关,防止一些登录的资源没有被发到客户端中比如css，js等
        String[] urls = {"/login.html","/img/","/images","/css/","/login","/layui","/picture","/UserServlet","/checkCode","/js","/element-ui"};
        String URL = request.getRequestURL().toString();
        for (String url : urls) {
            if (URL.contains(url)) {
                //找到资源并放行，后面的代码就不用执行了，直到登录成功为止
                chain.doFilter(req, resp);
                return;
            }
        }
        //1.判断session中是否有user
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
//        System.out.println("loginFilter:"+session.getId());
        //2.判断user是否为null
        if (user != null) {
            User user_A = (User) user;
            if(URL.contains("/admin$$&&.html")) {
                if ("admin".equals(user_A.getUsername())) {
                    //管理员用户登录过，放行
                    chain.doFilter(req, resp);
                }else{
                    //如果不是管理员就跳转到登录界面
                    req.getRequestDispatcher("/login.html").forward(req,resp);
                }
            }else{
                //普通用户登录过，放行
                chain.doFilter(req, resp);
            }
        }else{
            //没有登录，跳转到登录界面
            req.getRequestDispatcher("/login.html").forward(req,resp);
        }
    }
    public void destroy() {
    }
    public void init(FilterConfig config) throws ServletException {

    }

}
