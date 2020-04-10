package com.wcq.message.queue.scrabbitmqproducer.util;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RabbitmqDirectProducerUtil {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection(() -> {
            List list = new ArrayList<>();
            list.add(new Address("100.64.64.176",5672));
            return list;
        });
        String exchangeName = "direct-exchange";
        String routingKey1 = "direct-echg-rk-one";
        String routingKey2 = "direct-echg-rk-two";
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        for (int i = 0; i < 100; i++){
            String message1 = "direct-exchange = direct-echg-rk-one , 发送消息 ： " + LocalDateTime.now().toString();
            channel.basicPublish(exchangeName,routingKey1,null,message1.getBytes());
            String message2 = "direct-exchange = direct-echg-rk-two , 发送消息 ： " + LocalDateTime.now().toString();
            channel.basicPublish(exchangeName,routingKey2,null,message2.getBytes());
        }
        System.out.println("消息发送成功");
        channel.close();
        connection.close();
    }

}
