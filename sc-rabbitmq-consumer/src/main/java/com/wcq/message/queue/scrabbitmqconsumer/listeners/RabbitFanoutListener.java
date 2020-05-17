package com.wcq.message.queue.scrabbitmqconsumer.listeners;

import com.rabbitmq.client.Channel;
import com.wcq.message.queue.scrabbitmqconsumer.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RabbitFanoutListener {

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.FANOUT_QUEUE_NAME)
    public void getFanoutMsgs(Message message, Channel channel, String msg) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        String mssg = new String(message.getBody(),"UTF-8");
        log.info(mssg);
    }
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(RabbitConfig.FANOUT_QUEUE_NAME_1),
                    exchange = @Exchange(value = RabbitConfig.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)
            )
    )
    public void getFanoutMsg2(Message message, Channel channel,String msg) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        String mssg = new String(message.getBody(),"UTF-8");
        log.info(mssg);
    }

}
