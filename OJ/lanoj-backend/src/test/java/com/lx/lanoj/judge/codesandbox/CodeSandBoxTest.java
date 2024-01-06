package com.lx.lanoj.judge.codesandbox;

import com.lx.lanoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lx.lanoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.lx.lanoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CodeSandBoxTest {

    @Value("${codesandbox.type}")
    private String type;
    @Test
    void executeCode() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.getCodeSandBox(type);
        String code = "import java.util.Scanner;\n" +
                "public class Main {\n" +
                "    public Main(){\n" +
                "\n" +
                "    }\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果:\"+(a+b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest codeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse codeResponse = codeSandBox.executeCode(codeRequest);
        System.out.println(codeResponse);
    }
}