package com.lx.lanoj.common;

import java.io.Serializable;
import lombok.Data;

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