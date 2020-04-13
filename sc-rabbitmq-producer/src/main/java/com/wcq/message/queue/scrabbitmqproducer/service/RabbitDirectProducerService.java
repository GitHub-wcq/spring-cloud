package com.wcq.message.queue.scrabbitmqproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RabbitDirectProducerService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(String msg){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        log.info("direct message -- " + LocalDateTime.now().toString() + " | "+ msg);
        //用来测试，所以id写死，实际业务根据业务情况设置id
        CorrelationData correlationData = new CorrelationData("123456");
        rabbitTemplate.convertAndSend("direct-exchange",
                "direct-routingkey",
                "direct message -- " + LocalDateTime.now().toString() + " | "+ msg,
                correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息id:" + correlationData.getId());
        if (ack) {
            System.out.println("消息发送确认成功");
        } else {
            System.out.println("消息发送确认失败:" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try {
            log.info("return message : {}",new String(message.getBody(),"UTF-8"));
            log.info("replyCode : {}", replyCode);
            log.info("replyText : {}", replyText);
            log.info("exchange : {}", exchange);
            log.info("routingKey : {}", routingKey);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
