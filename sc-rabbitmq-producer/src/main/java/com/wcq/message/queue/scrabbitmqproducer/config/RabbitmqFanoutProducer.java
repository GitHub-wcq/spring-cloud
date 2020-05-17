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
public class RabbitmqFanoutProducer implements CommandLineRunner,RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {
        FanoutThread fanoutThread = new FanoutThread(this::confirm,this::returnedMessage);
        ThreadExecutorUtil.get().execute(fanoutThread);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("Fanout消息id:" + correlationData.getId());
        if (ack) {
            System.out.println("Fanout消息发送确认成功");
        } else {
            System.out.println("Fanout消息发送确认失败:" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try {
            log.info("Fanout- return message : {}",new String(message.getBody(),"UTF-8"));
            log.info("Fanout- replyCode : {}", replyCode);
            log.info("Fanout- replyText : {}", replyText);
            log.info("Fanout- exchange : {}", exchange);
            log.info("Fanout- routingKey : {}", routingKey);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    class FanoutThread implements Runnable {

        private RabbitTemplate.ConfirmCallback confirmCallback;
        private RabbitTemplate.ReturnCallback returnCallback;

        public FanoutThread(RabbitTemplate.ConfirmCallback confirmCallback, RabbitTemplate.ReturnCallback returnCallback) {
            this.confirmCallback = confirmCallback;
            this.returnCallback = returnCallback;
        }

        @SneakyThrows
        @Override
        public void run() {
            rabbitTemplate.setConfirmCallback(confirmCallback);
            rabbitTemplate.setReturnCallback(returnCallback);
            while (true){
                String msg = "Fanout message -- " + LocalDateTime.now().toString() + " | "+ Thread.currentThread().getName();
                log.info(msg);
                //用来测试，所以id写死，实际业务根据业务情况设置id
                CorrelationData correlationData = new CorrelationData("333333");
                rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE,"",msg,correlationData);
                Thread.sleep(3500);
            }
        }
    }
}
