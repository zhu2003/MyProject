package com.lx.annotation;

import java.lang.annotation.*;

/**
 * 接口访问频率注解，默认一分钟只能访问5次
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    long time() default 10000; //// 限制时间 单位：毫秒(默认值：一分钟）
    int value() default 5;// 允许请求的次数(默认值：5次）
}
