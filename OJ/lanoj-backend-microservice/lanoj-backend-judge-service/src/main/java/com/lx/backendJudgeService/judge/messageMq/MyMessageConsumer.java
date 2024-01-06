package com.lx.backendJudgeService.judge.messageMq;

//@Component
//@Slf4j
//public class MyMessageConsumer {
//    @Autowired
//    private JudgeService judgeService;
//    @SneakyThrows
//    @RabbitListener(queues = "code_queue",ackMode = "MANUAL")
//    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
//        log.info("接收到消息：" + message);
//        long questionSubmitId = Long.parseLong(message);
//        try{
//            judgeService.doJudge(questionSubmitId);
//            channel.basicAck(deliveryTag,false);
//        }catch (Exception e){
//            channel.basicNack(deliveryTag,false,false);
//        }
//    }
//
//}
