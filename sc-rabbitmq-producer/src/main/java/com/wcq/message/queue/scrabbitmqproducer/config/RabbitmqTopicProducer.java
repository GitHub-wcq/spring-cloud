package com.wcq.message.queue.scrabbitmqproducer.config;

import com.wcq.message.queue.scrabbitmqproducer.util.ThreadExecutorUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class RabbitmqTopicProducer implements CommandLineRunner,RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {
        TopicThread topicThread = new TopicThread(this,this);
        ThreadExecutorUtil.get().execute(topicThread);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("Topic消息id:" + correlationData.getId());
        if (ack) {
            System.out.println("Topic消息发送确认成功");
        } else {
            System.out.println("Topic消息发送确认失败:" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try {
            log.info("Topic - return message : {}",new String(message.getBody(),"UTF-8"));
            log.info("Topic - replyCode : {}", replyCode);
            log.info("Topic - replyText : {}", replyText);
            log.info("Topic - exchange : {}", exchange);
            log.info("Topic - routingKey : {}", routingKey);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    class TopicThread implements Runnable{

        private RabbitTemplate.ConfirmCallback confirmCallback;
        private RabbitTemplate.ReturnCallback returnCallback;
        public TopicThread(RabbitTemplate.ConfirmCallback confirmCallback,RabbitTemplate.ReturnCallback returnCallback){
            this.confirmCallback = confirmCallback;
            this.returnCallback = returnCallback;
        }

        @SneakyThrows
        @Override
        public void run() {
            rabbitTemplate.setConfirmCallback(confirmCallback);
            rabbitTemplate.setReturnCallback(returnCallback);
            while (true){
                String msg = "Topic message -- " + LocalDateTime.now().toString() + " | "+ Thread.currentThread().getName();
                log.info(msg);
                //用来测试，所以id写死，实际业务根据业务情况设置id
                CorrelationData correlationData = new CorrelationData("222222");
                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE,RabbitConfig.TOPIC_ROUTING_KEY,msg,correlationData);
                Thread.sleep(3000);
            }
        }
    }
}
