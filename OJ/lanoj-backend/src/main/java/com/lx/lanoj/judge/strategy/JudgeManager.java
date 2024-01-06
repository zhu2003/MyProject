package com.lx.lanoj.judge.strategy;

import com.lx.lanoj.judge.codesandbox.model.JudgeInfo;
import com.lx.lanoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {
    public JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        switch (language) {
            case "java":
                return new JavaLanguageJudgeStrategy().doJudge(judgeContext);
            default:
                return new DefaultJudgeStrategy().doJudge(judgeContext);
        }
    }
}
