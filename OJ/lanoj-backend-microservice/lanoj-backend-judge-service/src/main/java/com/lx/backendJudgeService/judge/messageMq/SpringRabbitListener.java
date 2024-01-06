package com.lx.backendJudgeService.judge.messageMq;

import com.lx.backendJudgeService.judge.service.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SpringRabbitListener {
    @Autowired
    private JudgeService judgeService;
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue("direct.queue1"),
                    exchange = @Exchange(value = "code_exchange",type = ExchangeTypes.DIRECT),
                    key = {"my_key"}
            )
    )
    public void listenDirectQueue(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("接收到消息：" + msg);
        long questionSubmitId = Long.parseLong(msg);
        try{
            judgeService.doJudge(questionSubmitId);
            channel.basicAck(deliveryTag,false);
        }catch (Exception e){
            log.error("{} 判题失败",msg);
            //拒绝（NACK）一条消息的方法调用，不重新放回队列给消费者，标记已拒绝
            channel.basicNack(deliveryTag,false,false);
        }

    }
}
