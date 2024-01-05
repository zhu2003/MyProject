package com.lx.service.impl;

import com.lx.mapper.UserMapper;
import com.lx.pojo.PageBean;
import com.lx.pojo.User;
import com.lx.pojo.User_Cscore;
import com.lx.service.UserService;
import com.lx.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserServiceimpl implements UserService {
    //1.创建sqlsessionFactory工厂对象
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    //根据用户名和密码查询登录状态
    public User selectByNameAndPasswd(String username, String password) {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.执行方法,判断是否登录成功
        User user = mapper.selectByNameAndPasswd(username, password);
        //释放资源
        sqlSession.close();
        return user;
    }

    public void updatePw(User user) {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.执行方法,修改密码
        mapper.updatePw(user);
        //5.提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    public List<User> selectAll() {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.执行方法
        List<User> users = mapper.selectAll();
        return users;
    }

    public PageBean<User_Cscore> selectByPageByCondition(int CurrentPage, int pageSize, User_Cscore user) {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.计算开始索引
        int begin = (CurrentPage-1)*pageSize;
        int size = pageSize;
        //处理brand条件，模糊表达式
        String stuName = user.getStuName();
        if (stuName != null && stuName.length()>0) {
            user.setStuName("%"+stuName+"%");
        }
        //5.查询当前页数据
        List<User_Cscore> users = mapper.selectByPageAndCondition(begin,size,user);
        //6.查询总记录数
        int totalCount = mapper.selectTotalCountByCondition(user);
        //7.封装成pageBean对象
        PageBean<User_Cscore> pageBean = new PageBean<User_Cscore>();
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(users);
        //8.释放资源
        sqlSession.close();
        return pageBean;
    }

    public void updateScore(int id) {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.执行方法
        mapper.updateScore(id);
        //5.提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }
    public void reduceScore(int id) {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.执行方法
        mapper.reduceScore(id);
        //5.提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    public void updateUser(User user) {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.执行方法
        mapper.updateUser(user);
        //5.提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    public void resetStatus() {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.执行方法
        mapper.resetStatus();
        //5.提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }
    //改变状态值
    public void ChangeStatus(int id) {
        //2.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.执行方法
        mapper.ChangeStatus(id);
        //5.提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }
}
