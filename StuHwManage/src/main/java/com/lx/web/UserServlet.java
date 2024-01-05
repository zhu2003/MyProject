package com.lx.web;

import com.alibaba.fastjson.JSON;
import com.lx.pojo.PageBean;
import com.lx.pojo.User;
import com.lx.pojo.User_Cscore;
import com.lx.service.UserService;
import com.lx.service.impl.UserServiceimpl;
import com.lx.pojo.loginInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@WebServlet("/Hw/*")
public class UserServlet extends BaseServlet{
    private UserService service = new UserServiceimpl();
    //根据id改变状态的值
    public void ChangeStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取session会话中的用户信息判断是否id一样
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        //1.接收id的值
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        //转换成java对象
        User user2 = JSON.parseObject(s, User.class);
        //判断id是否一样
        if (user2.getId()==user1.getId()) {
            //2.调用更新方法
            service.ChangeStatus(user2.getId());
            //4.写数据，数据可能包含中文所以要设置contenttype
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write("success");
        }else{
            //4.写数据，数据可能包含中文所以要设置contenttype
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write("false");
        }

    }
    //重置主状态
    public void resetStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取session会话中的用户信息判断是否是越权访问
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        if ("admin".equals(user1.getUsername())){
            //调用方法
            service.resetStatus();
            resp.getWriter().write("success");
        }else{
            resp.getWriter().write("false");
        }

    }
    //修改用户信息
    public void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取session会话中的用户信息判断是否是越权访问
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        //1.接收数据
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        //2.转换为java对象
        User user = JSON.parseObject(s, User.class);
        //判断操作用户是否为管理员
        if ("admin".equals(user1.getUsername())){
            //3.调用方法
            service.updateUser(user);
            resp.getWriter().write("success");
        }else{
            resp.getWriter().write("false");
        }
    }
    //查询所有用户
    public void FindAllUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取session会话中的用户信息判断是否是越权访问
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        if ("admin".equals(user1.getUsername())) {
            //1.调用查询方法
            List<User> users = service.selectAll();
            //2.转换为json对象
            String s = JSON.toJSONString(users);
            //3.写数据，数据可能包含中文所以要设置contenttype
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write(s);
        }else{
            //3.写数据，数据可能包含中文所以要设置contenttype
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write("false");
        }
    }
    //减少操行分
    public void reduceScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取session会话中的用户信息判断是否是越权访问
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        //接收id
        String id = req.getParameter("id");
        if("admin".equals(user1.getUsername())) {
            //更新分数
            service.reduceScore(Integer.parseInt(id));
            //3.写数据，数据可能包含中文所以要设置contenttype
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write("success");
        }else{
            resp.getWriter().write("false");
        }
    }
    //增加操行分
    public void updateScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取session会话中的用户信息判断是否是越权访问
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        //接收id
        String id = req.getParameter("id");
        if("admin".equals(user1.getUsername())){
            //更新分数
            service.updateScore(Integer.parseInt(id));
            //3.写数据，数据可能包含中文所以要设置contenttype
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write("success");
        }else{
            resp.getWriter().write("false");
        }

    }
    //查询用户操行分
    public void FindUserScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //1.接收当前页码和每页条数url?currentPage=1&pageSize=5
        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        //获取查询条件对象
        BufferedReader reader = req.getReader();
        String s = reader.readLine();//json字符串
        //转换为User_Cscore对象
        User_Cscore user_cscore = JSON.parseObject(s, User_Cscore.class);
        //获取session会话中的用户信息判断是否是越权访问
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        if("admin".equals(user1.getUsername())){
            //2.调用service查询
            PageBean<User_Cscore> brandPageBean = service.selectByPageByCondition(currentPage,pageSize,user_cscore);
            //2.数据转换成json
            String data = JSON.toJSONString(brandPageBean);
            //3.写数据，数据可能包含中文所以要设置contenttype
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write(data);
        }else{
            resp.getWriter().write("false");
        }

    }
    //查询用户信息
    public void FindUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        User user2 = (User) user;
        loginInfo loginInfo = new loginInfo();
        loginInfo.setName(user2.getName());
        loginInfo.setId(user2.getId());
        loginInfo.setStatus(user2.getStatus()+"");
        //java对象转换为json对象
        String li = JSON.toJSONString(loginInfo);
        //3.响应成功的标识,设置编码可能有中文
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(li);
    }
    //登录方法
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收用户名和密码，将json对象转换为User对象
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        User user1 = JSON.parseObject(s, User.class);
        //获取程序生成的验证码
        String Code1 = checkCodeServlet.getCode();
        if (!Code1.equalsIgnoreCase(user1.getCheckCode())) {
            //不允许登录
            req.getRequestDispatcher("/login.html").forward(req,resp);
            return;
        }
        //调用方法查询
        User user2 = service.selectByNameAndPasswd(user1.getUsername(),user1.getPassword());
        if (user2 != null) {
            //将登陆成功后的user对象，存储到session中
            HttpSession session = req.getSession();
            session.setAttribute("user",user2);
//            System.out.println("UserServlet:"+session.getId());
            //没有数据要共享所以用重定向
            String contextPath = req.getContextPath();//动态获取虚拟路径
            //封装登录信息
            loginInfo loginInfo = new loginInfo();
            System.out.println(contextPath);
            if ("admin".equals(user2.getUsername())){
                loginInfo.setPath("admin$$&&.html");
            }else{
                loginInfo.setPath("HtManage.html");
            }
            loginInfo.setStatus(new String(1+""));
            loginInfo.setName(user2.getName());
            //java对象转换为json对象
            String li = JSON.toJSONString(loginInfo);
            //3.响应成功的标识,设置编码因为可能有中文
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write(li);
//            resp.sendRedirect(contextPath+"/HtManage.html");
            LogsWriter("D:\\tomcat\\logs\\UserLogs.txt"," 登录成功",user2);
        }
        else {
            //登录失败
            //转发到login.html
            req.getRequestDispatcher("/login.html").forward(req,resp);
        }
    }
    //退出登录
    public void loginOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取session对象
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        //3.响应成功的标识,设置编码因为可能有中文
//        resp.setContentType("text/json;charset=utf-8");
//        resp.getWriter().write("true");
        resp.sendRedirect("/login.html");
    }
    //文件上传
    public void FileUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String fileName="";
        //1.先判断上传的数据是否多段数据
        if(ServletFileUpload.isMultipartContent(req)){
            //创建FileItemFactory工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            //创建用于解析上传的数据
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            //解析上传的数据，得到每一个表单项
            try {
                List<FileItem> fileItems = servletFileUpload.parseRequest(req);
                //循环判断每一个表单项，是普通类型还是上传的文件
                for (FileItem fileItem : fileItems) {
                    if (fileItem.isFormField()){
                        //普通表单项
                        System.out.println("name:"+fileItem.getFieldName());
                        System.out.println("value:"+fileItem.getString("utf-8"));
                    }else{
                        //上传的文件
                        System.out.println("name:"+fileItem.getFieldName());
                        System.out.println("Filename:"+fileItem.getName());
                        fileName=fileItem.getName();
                        HttpSession session = req.getSession();
                        User user = (User) session.getAttribute("user");
                        fileItem.write(new File("D:\\file\\"+user.getName()+" "+fileName));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //获取用户信息
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        //写日志文件
        LogsWriter("D:\\tomcat\\logs\\UploadLogs.txt","--->"+fileName,user1);
    }
    //修改密码
    public void updatePw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //1.接收用户信息
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        User user = JSON.parseObject(s, User.class);
        String value = new String (user.getName().getBytes("utf-8"),"UTF-8");
        user.setName(value);
        //获取session会话中的用户信息判断是否是越权访问
        HttpSession session = req.getSession();
        User user1 = (User)session.getAttribute("user");
        if(user.getId()==user1.getId()){
            //2.调用更新方法
            service.updatePw(user);
            //3.返回信息
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write("success");
        }else{
            resp.getWriter().write("false");
        }

    }
    //写入日志文件方法
    public void LogsWriter(String path,String opera,User user1) throws IOException {
        //写入文件上传日志文件
        //获取时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();//父类引用指向子类对象
        String date = formatter.format(c.getTime());
        //写文件
        BufferedWriter bw = new BufferedWriter(new FileWriter(path,true));
        bw.write(date+" "+user1.getName()+":"+user1.getUsername()+opera);
        bw.newLine();//写出回车换行符
        bw.close();
    }
}
