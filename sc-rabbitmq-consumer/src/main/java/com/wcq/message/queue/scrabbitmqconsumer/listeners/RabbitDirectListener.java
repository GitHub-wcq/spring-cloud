package com.wcq.message.queue.scrabbitmqconsumer.listeners;

import com.rabbitmq.client.Channel;
import com.wcq.message.queue.scrabbitmqconsumer.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class RabbitDirectListener {

//    @RabbitHandler
//    @RabbitListener(
//            bindings = @QueueBinding(
//                    value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}"),
//                    exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}"),
//                    key = "direct-routingkey"
//            ))
    public void getDirectMsg(Message message, Channel channel,String msg) throws IOException {
        try {
            int a =1/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info(new String(message.getBody(),"UTF-8"));
            log.info("[ consumer ] : {}",msg);
        } catch (Exception e){
            //如果我们把channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);中最后一个参数改为false呢，会发现在web管理界面没有未被消费的消息，说明这条消息已经被摒弃。
            //(true代表打回队列继续消费)实际开发中，到底是打回到队列呢还是摒弃，要看自己的需求，但是打回队列应该有次数限制，不然会陷入死循环。
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            e.printStackTrace();
        }
    }

    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitConfig.DIRECT_QUEUE_NAME),
                    exchange = @Exchange(value = RabbitConfig.DIRECT_EXCHANGE),
                    key = RabbitConfig.DIRECT_ROUTING_KEY
            )
    )
    public void getDirectQueueMsg(Message message, Channel channel,String msg) throws IOException {
        log.info(message.toString());
        /**
         * deliveryTag（唯一标识 ID）：当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel ，
         * RabbitMQ 会用 basic.deliver 方法向消费者推送消息，这个方法携带了一个 delivery tag，
         * 它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，
         * delivery tag 的范围仅限于 Channel
         *
         * multiple：为了减少网络流量，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
         */
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        String mssg = new String(message.getBody(),"UTF-8");
        log.info(mssg);
    }

}
