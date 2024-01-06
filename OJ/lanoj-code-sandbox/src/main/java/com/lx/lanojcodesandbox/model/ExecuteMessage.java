package com.lx.lanojcodesandbox.model;

import lombok.Data;

@Data
public class ExecuteMessage {
    private Integer exitCode;
    private String message;
    private String errorMessage;
    private Long time;
}
