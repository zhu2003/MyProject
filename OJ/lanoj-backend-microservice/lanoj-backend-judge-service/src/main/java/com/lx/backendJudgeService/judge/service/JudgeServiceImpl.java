package com.lx.backendJudgeService.judge.service;

import cn.hutool.json.JSONUtil;
import com.lx.backendCommon.common.ErrorCode;
import com.lx.backendCommon.exception.BusinessException;
import com.lx.backendJudgeService.judge.codesandbox.CodeSandBox;
import com.lx.backendJudgeService.judge.codesandbox.CodeSandBoxFactory;
import com.lx.backendJudgeService.judge.codesandbox.CodeSandBoxProxy;
import com.lx.backendJudgeService.judge.strategy.JudgeContext;
import com.lx.backendJudgeService.judge.strategy.JudgeManager;
import com.lx.backendModel.model.codesandbox.ExecuteCodeRequest;
import com.lx.backendModel.model.codesandbox.ExecuteCodeResponse;
import com.lx.backendModel.model.codesandbox.JudgeInfo;
import com.lx.backendModel.model.dto.question.JudgeCase;
import com.lx.backendModel.model.entity.Question;
import com.lx.backendModel.model.entity.QuestionSubmit;
import com.lx.backendModel.model.enums.QuestionSubmitStatusEnum;
import com.lx.backendServiceClient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService{
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private JudgeManager judgeManager;
    @Value("${codesandbox.type}")
    private String type;
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1.根据题目提交id获取题目提交信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if(questionSubmit==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目提交不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if(question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        //2.如果不为等待状态
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题中");
        }
        //3.更改判题状态为判题中，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新题目提交状态失败");
        }
        //4.调用代码沙箱
        CodeSandBox codeSandBox = CodeSandBoxFactory.getCodeSandBox(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest codeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse codeResponse = codeSandBox.executeCode(codeRequest);
        //5.根据沙箱返回结果，判断题目 状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(codeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(codeResponse.getOutputList());
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
//        DefaultJudgeStrategy defaultJudgeStrategy = new DefaultJudgeStrategy();
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //修改判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新题目提交状态失败");
        }
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResult;
    }
}
