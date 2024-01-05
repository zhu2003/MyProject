package com.lx.web;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/*
 * 替换HttpServlet,根据请求的最后一段路径进行方法分发
 * */
public class BaseServlet extends HttpServlet {
    //根据请求的最后一段路径进行方法分发
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求路径
        String requestURI = req.getRequestURI();//brand-case/brand/selectAll
        //2.获取最后一段路径，方法名
        int index = requestURI.lastIndexOf('/');
        String methodName = requestURI.substring(index + 1);
        //3.执行方法
        //3.1获取BrandServlet的字节码对象 class（this谁调用我，我代表谁）
        Class<? extends BaseServlet> aClass = this.getClass();
        //3.2获取method方法
        try {
            Method method = aClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
