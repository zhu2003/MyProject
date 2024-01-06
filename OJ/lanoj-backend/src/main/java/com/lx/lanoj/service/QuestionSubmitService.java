package com.lx.lanoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.lanoj.model.dto.question.QuestionQueryRequest;
import com.lx.lanoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.lx.lanoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.lx.lanoj.model.entity.Question;
import com.lx.lanoj.model.entity.QuestionSubmit;
import com.lx.lanoj.model.entity.User;
import com.lx.lanoj.model.vo.QuestionSubmitVO;
import com.lx.lanoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 蓝朽
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-12-27 08:57:21
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /*
    * 题目提交
    * */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 题目提交（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doQuestionSubmitInner(long userId, long postId);

    /**
     * 获取查询条件
     *
     * @param queryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest queryRequest);


    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
