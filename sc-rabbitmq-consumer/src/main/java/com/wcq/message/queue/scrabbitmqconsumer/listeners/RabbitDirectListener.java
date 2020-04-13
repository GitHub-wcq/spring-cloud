package com.wcq.message.queue.scrabbitmqconsumer.listeners;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RabbitDirectListener {

    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}"),
                    exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}"),
                    key = "direct-routingkey"
            ))
    public void getDirectMsg(Message message, Channel channel,String msg) throws IOException {
        try {
            int a =1/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info(new String(message.getBody(),"UTF-8"));
            log.info("[ consumer ] : {}",msg);
        } catch (Exception e){
            //如果我们把channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);中最后一个参数改为false呢，会发现在web管理界面没有未被消费的消息，说明这条消息已经被摒弃。
            //实际开发中，到底是打回到队列呢还是摒弃，要看自己的需求，但是打回队列应该有次数限制，不然会陷入死循环。
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            e.printStackTrace();
        }
    }

}
