package com.lx.backendQuestionService;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class demo {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send(){
        //交换机名称
        String exchangeName = "code_exchange";
        //消息
        String message = "hello everyone";
        String key = "my_key";
        //发送
        rabbitTemplate.convertAndSend(exchangeName,key,message);
    }
}
