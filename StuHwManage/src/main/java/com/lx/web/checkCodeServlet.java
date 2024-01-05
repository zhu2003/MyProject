package com.lx.web;

import com.lx.util.CheckCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkCode")
public class checkCodeServlet extends HttpServlet {
    private static String code;
    public void setCode(String code){
        this.code=code;
    }
    public static String getCode(){
        return code;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //生成验证码
        ServletOutputStream os = response.getOutputStream();
        String CheckCode = CheckCodeUtil.outputVerifyImage(90, 30, os, 4);
        //存入session中
        System.out.println(CheckCode);
        setCode(CheckCode);
//        HttpSession session = request.getSession();
//        session.setAttribute("CheckCode",CheckCode);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
