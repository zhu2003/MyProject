package com.lx.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Search {
    private String keyWord;
    private List<String> date;
}
