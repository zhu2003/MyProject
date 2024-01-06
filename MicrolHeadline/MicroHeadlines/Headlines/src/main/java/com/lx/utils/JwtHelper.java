package com.lx.utils;

import com.alibaba.druid.util.StringUtils;
import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtHelper {
    private long JWT_TTL; //有效时间,单位毫秒 1000毫秒 == 1秒
    private String JWT_KEY;  //当前程序签名秘钥
    //解密JWT_KEY
    public SecretKey generalKey(){
        byte[] encodedKey = Base64.getEncoder().encode(JWT_KEY.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return secretKeySpec;
    }
    //生成token字符串
    public String createToken(Long userId) {
        //获取加密后的密钥
        SecretKey secretKey = generalKey();
        String token = Jwts.builder()
                .setSubject("YYGH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TTL*1000*60)) //单位分钟
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //从token字符串获取userid
    public Long  getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    //判断token是否有效
    public boolean isExpiration(String token){
        try {
            boolean isExpire = Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
            //没有过期，有效，返回false
            return isExpire;
        }catch(Exception e) {
            //过期出现异常，返回true
            return true;
        }
    }
}
