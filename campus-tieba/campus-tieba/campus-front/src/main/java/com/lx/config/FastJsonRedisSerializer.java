package com.lx.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;
    static
    {
        //用于启用 Fastjson 的自动类型支持，以便在反序列化过程中识别 JSON 数据的类型信息并创建相应的 Java 对象。
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    public FastJsonRedisSerializer(Class<T> clazz)
    {
        this.clazz = clazz;
    }
    /*
    * 序列化
    * */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t==null){
            return  new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }
    /*
    * 反序列化
    * */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes==null){
            return null;
        }
        String s = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(s,clazz);
    }
}
