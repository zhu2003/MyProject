package com.lx.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletContext {
    public static ServletRequestAttributes getAttributes(){
        return (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    }
    public static HttpServletRequest getRequest(){
        return getAttributes().getRequest();
    }
    public static HttpServletResponse getResponse(){
        return getAttributes().getResponse();
    }
}
