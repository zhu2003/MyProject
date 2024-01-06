package com.lx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.entity.PageInfo;
import com.lx.entity.Type;
import com.lx.utils.Result;

public interface TypeService extends IService<Type> {
    /*
     * 获取所有分类
     * */
    Result findAllTypes();

}
