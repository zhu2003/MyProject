package com.lx.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    public BeanCopyUtils() {

    }
    public static <T> T copyBean(Object source,Class<T> clazz){
        T target = null;
        try {
            //创建目标对象
            target = clazz.newInstance();
            // 实现属性拷贝
            BeanUtils.copyProperties(source,target);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 返回结果
        return target;
    }
    public static <O,T> List<T> copyBeanList(List<O> list,Class<T> clazz){
        return list.stream()
                .map(o->copyBean(o,clazz))
                .collect(Collectors.toList());
    }

}
