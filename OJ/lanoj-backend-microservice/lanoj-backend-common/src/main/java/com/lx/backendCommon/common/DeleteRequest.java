package com.lx.backendCommon.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author 蓝朽
 * @from 蓝朽
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}