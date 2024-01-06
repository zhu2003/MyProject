package com.lx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.lx.entity.User;
import com.lx.utils.JwtUtil;
import com.lx.utils.PasswordEncode;
import com.lx.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

@SpringBootTest
public class test {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCache redisCache;

    public static void main(String[] args) {

    }
    @Test
    public void testRedis(){
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("789");
        list.remove("123");
        System.out.println(list);

    }
}
