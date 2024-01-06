package com.lx.lanoj.model.dto.questionsubmit;

import com.lx.lanoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询
 *
 * @author 蓝朽
 * @from 蓝朽
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 题目id
     */
    private Long questionId;

    /*
    * 用户id
    * */
    private Long userId;

    private static final long serialVersionUID = 1L;
}