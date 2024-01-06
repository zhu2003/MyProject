package com.lx.backendQuestionService.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.backendModel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.lx.backendModel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.lx.backendModel.model.entity.QuestionSubmit;
import com.lx.backendModel.model.entity.User;
import com.lx.backendModel.model.vo.QuestionSubmitVO;

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
