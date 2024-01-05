package com.lx.mapper;
import com.lx.pojo.User;
import com.lx.pojo.User_Cscore;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    //查询所有
    @Select("select * from stu_user")
    List<User> selectAll();
    //登录信息
    @Select("select * from stu_user where username=#{username} and password=#{password}")
    User selectByNameAndPasswd(@Param("username") String username,@Param("password") String password);
    //修改密码
    @Update("update stu_user set password=#{password} where id=#{id}")
    void updatePw(User user);
    //查询总记录数
    @Select("select count(*) from stu_user")
    int selectTotalCount();
    //分页条件查询
    List<User_Cscore> selectByPageAndCondition(@Param("begin") int begin,@Param("size")int size,@Param("user")User_Cscore user);
    //根据条件查询总记录数
    Integer selectTotalCountByCondition(User_Cscore user);
    //更新分数
    void updateScore(int id);
    //减少分数
    void reduceScore(int id);
    //修改用户信息
    void updateUser(@Param("user")User user);
    //添加用户
    void addUser(User user);
    //重置状态
    @Update("update stu_user set status=0")
    void resetStatus();
    //改变状态值
    void ChangeStatus(int id);
}
