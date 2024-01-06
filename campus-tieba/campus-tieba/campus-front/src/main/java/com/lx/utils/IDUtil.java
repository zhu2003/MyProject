package com.lx.utils;

import ch.qos.logback.core.encoder.ByteArrayUtil;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/*
* id生成工具
* */
public class IDUtil {

    public static String generateID(){
        UUID uuid = UUID.randomUUID();
        String id = null;
        try{
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] bytes = md5.digest(uuid.toString().getBytes());
            id = ByteArrayUtil.toHexString(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }
}
