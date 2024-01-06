package com.lx.Vo;

import lombok.Data;

import java.util.List;
@Data
public class TiebaListVo {
    List<TiebaVo> rows;
    Integer total;
}
