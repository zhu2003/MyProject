package com.lx.backendModel.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题信息业务类型枚举
 *
 * @author 蓝朽
 * @from 蓝朽
 */
public enum JudgeInfoMessageEnum {

    ACCEPTED("成功", "ACCEPTED"),
    WRONG_ANSWER("答案错误", "WRONG_ANSWER"),
    COMPILE_ERROR("编译错误", "COMPILE_ERROR"),
    TIME_LIMIT_EXCEEDED("时间超限", "TIME_LIMIT_EXCEEDED"),
    MEMORY_LIMIT_EXCEEDED("内存超限", "MEMORY_LIMIT_EXCEEDED"),
    RUNTIME_ERROR("运行时错误", "RUNTIME_ERROR"),
    WAITING("等待中", "WAITING"),
    SYSTEM_ERROR("系统错误", "SYSTEM_ERROR");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
