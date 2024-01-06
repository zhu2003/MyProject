package com.lx.backendModel.model.vo;

import cn.hutool.json.JSONUtil;
import com.lx.backendModel.model.dto.question.JudgeConfig;
import com.lx.backendModel.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目
 * @TableName question
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 答案
     */
    private String answer;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;


    /**
     * 判题配置(json)
     */
    private JudgeConfig judgeConfig;

    /*
    * 判题用例
    * */
    private String judgeCase;
    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /*
    * 创建题目人信息
    * */
    private UserVO userVO;
    /**
     * 包装类转对象
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        if(tagList!=null){
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig config = questionVO.getJudgeConfig();
        if (config!= null){
            question.setJudgeConfig(JSONUtil.toJsonStr(config));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        List<String> tagsList = JSONUtil.toList(question.getTags(),String.class);
        questionVO.setTags(tagsList);
        String config = question.getJudgeConfig();
        if(config!=null){
            questionVO.setJudgeConfig(JSONUtil.toBean(config, JudgeConfig.class));
        }
        return questionVO;
    }
    private static final long serialVersionUID = 1L;
}