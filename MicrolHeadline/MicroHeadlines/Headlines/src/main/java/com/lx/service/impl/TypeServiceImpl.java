package com.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.Vo.TypeVo;
import com.lx.entity.PageInfo;
import com.lx.entity.Type;
import com.lx.mapper.TypeMapper;
import com.lx.service.TypeService;
import com.lx.utils.BeanCopyUtils;
import com.lx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Autowired
    private TypeMapper typeMapper;
    /*
     * 获取所有分类
     * */
    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        List<TypeVo> typeVos = types.stream()
                .map(item -> BeanCopyUtils.copyBean(item, TypeVo.class))
                .collect(Collectors.toList());
        return Result.ok(typeVos);
    }

}
