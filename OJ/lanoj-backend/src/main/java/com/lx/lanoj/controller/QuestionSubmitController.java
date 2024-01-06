package com.lx.lanoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.lanoj.annotation.AuthCheck;
import com.lx.lanoj.common.BaseResponse;
import com.lx.lanoj.common.ErrorCode;
import com.lx.lanoj.common.ResultUtils;
import com.lx.lanoj.constant.UserConstant;
import com.lx.lanoj.exception.BusinessException;
import com.lx.lanoj.exception.ThrowUtils;
import com.lx.lanoj.model.dto.question.QuestionQueryRequest;
import com.lx.lanoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.lx.lanoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.lx.lanoj.model.dto.user.UserAddRequest;
import com.lx.lanoj.model.entity.QuestionSubmit;
import com.lx.lanoj.model.entity.User;
import com.lx.lanoj.model.vo.QuestionSubmitVO;
import com.lx.lanoj.service.QuestionSubmitService;
import com.lx.lanoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
@Deprecated
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     *做题
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 提交记录的id
     */
//    @PostMapping("/")
//    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
//            HttpServletRequest request) {
//        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // 登录才能提交
//        final User loginUser = userService.getLoginUser(request);
//        long questionId = questionSubmitAddRequest.getQuestionId();
//        Long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
//        return ResultUtils.success(result);
//    }
    /*
    * 分页获取题目提交列表
    * */
//    @PostMapping("/list/page")
//    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest queryRequest, HttpServletRequest request) {
//        long current = queryRequest.getCurrent();
//        long pageSize = queryRequest.getPageSize();
//        Page<QuestionSubmit> page = questionSubmitService.page(new Page<>(current, pageSize));
//        questionSubmitService.getQueryWrapper(queryRequest);
//        User loginUser = userService.getLoginUser(request);
//        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(page,loginUser));
//    }
}
