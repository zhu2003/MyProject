package com.lx.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    //设置值
    public void setValue(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    //设置值
    public void setValue(String key,Object value,Long timeout){
        redisTemplate.opsForValue().set(key,value,timeout,TimeUnit.SECONDS);
    }
    //获取值
    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }
    //删除值
    public void delValue(String key){
        redisTemplate.delete(key);
    }
}
