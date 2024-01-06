package com.lx;

import com.alibaba.druid.support.json.JSONUtils;
import com.lx.Vo.PageInfoVo;
import com.lx.utils.JwtHelper;
import com.lx.utils.MD5Util;
import com.lx.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class HeadlinesTest {
//    @Autowired
//    private JwtHelper jwtHelper;
//    @Autowired
//    private RedisTemplate redisTemplate;
//    //测试token
//    @Test
//    public void testJwt(){
//        //获取token
//        String token = jwtHelper.createToken(123456L);
//        System.out.println(token);
//        Long userId = jwtHelper.getUserId(token);
//        System.out.println(userId);
//        boolean b = jwtHelper.isExpiration("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSiox099ANDXYNUtJRSq0oULIyNLM0NjI0tzAy1lEqLU4t8kwBitUCAGlA_NUvAAAA.sPfNa_s8TYc-xpN6KNEL1MsvtWv-mtTU6GyvIQQ47Do2yp3rzWyFTAuCijbd_YczaY88XIKk-_u0if0Ci1e6Wg");
//        System.out.println(b);
//    }
//
//    @Test
//    public void test()  {
//        byte[] encodedKey = Base64.getEncoder().encode("lxworkhome".getBytes());
//        SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//        Calendar calendar2 = Calendar.getInstance();
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//        try{
//            Date date = sdf2.parse("2023-06-25 09:28:06");
//            Date date1 = new Date();
//            System.out.println((date1.getTime()-date.getTime())/(1000*60*60*24));
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//
//
//    }
//    /*
//    * md5测试
//    * */
//    @Test
//    public void md5Test(){
//        String s = MD5Util.encrypt("123456");
//        System.out.println(s);
//        HashMap<Object, Object> map = new HashMap<>();
//        map.put("key",123);
//        System.out.println(Result.ok(new HashMap<>().put("token",123)));
//    }
//    /*
//    * redis测试
//    * */
//    @Test
//    public void testRedis(){
//        BoundHashOperations hashOps = redisTemplate.boundHashOps("interfaceLimit:/hello");
//        hashOps.increment("10.123.1.14",1);
//        hashOps.expire(1, TimeUnit.HOURS);
//        Integer o = (Integer) hashOps.get("10.123.1.14");
//        System.out.println(o);
//    }
}
