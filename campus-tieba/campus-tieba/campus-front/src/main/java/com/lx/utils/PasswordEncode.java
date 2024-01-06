package com.lx.utils;

import ch.qos.logback.core.encoder.ByteArrayUtil;

import java.security.MessageDigest;

public class PasswordEncode {
    public static String encode(String password){
        try{
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] bytes = md5.digest(password.toString().getBytes());
            password = ByteArrayUtil.toHexString(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return password;
    }
}
