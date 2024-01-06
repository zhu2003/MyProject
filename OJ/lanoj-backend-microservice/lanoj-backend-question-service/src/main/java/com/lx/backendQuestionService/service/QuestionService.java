package com.lx.backendQuestionService.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.backendModel.model.dto.question.QuestionQueryRequest;
import com.lx.backendModel.model.entity.Question;
import com.lx.backendModel.model.vo.QuestionVO;


import javax.servlet.http.HttpServletRequest;

/**
* @author 蓝朽
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-12-27 08:55:31
*/
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param puestion
     * @param add
     */
    void validQuestion(Question puestion, boolean add);

    /**
     * 获取查询条件
     *
     * @param puestionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest puestionQueryRequest);
    

    /**
     * 获取题目封装
     *
     * @param puestion
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question puestion, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param puestionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> puestionPage, HttpServletRequest request);
}
