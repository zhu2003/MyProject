package com.lx.lanojcodesandbox.model;

import lombok.Data;

/*
* 题目用例
* */
@Data
public class JudgeInfo {
    /*
    * 程序执行信息
    * */
    private String message;
    /*
     * 程序执行时间 （ms）
     * */
    private Long time;
    /*
     * 程序执行内存(kb)
     * */
    private Long memory;
}
