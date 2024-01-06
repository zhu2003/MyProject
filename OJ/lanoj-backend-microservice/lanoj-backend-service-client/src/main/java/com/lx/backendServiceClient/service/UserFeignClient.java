package com.lx.backendServiceClient.service;

import com.lx.backendCommon.common.ErrorCode;
import com.lx.backendCommon.exception.BusinessException;
import com.lx.backendModel.model.entity.User;
import com.lx.backendModel.model.enums.UserRoleEnum;
import com.lx.backendModel.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.lx.backendCommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
 *
 * @author 蓝朽
 * @from 蓝朽
 */
@FeignClient(name = "lanoj-backend-user-service",path = "/api/user/inner")
public interface UserFeignClient {
    /*
    * 根据id获取用户
    * */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /*
    * 根据id获取用户列表
    * */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);
    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request){
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }
    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user){
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }


    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user){
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }



}
