package com.wcq.message.queue.scrabbitmqproducer.util;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RabbitmqFanoutProducerUtil {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("haier");
        factory.setPassword("Haier");
        Connection connection = factory.newConnection(() -> {
            List<Address> list = new ArrayList<>();
            list.add(new Address("100.64.64.176",5672));
            return list;
        });
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("fanout-exchange", BuiltinExchangeType.FANOUT);
        for (int i = 0; i<100; i++){
            String messmage = "fanout-exchange 发送消息" + LocalDateTime.now().toString();
            channel.basicPublish("fanout-exchange","",null,messmage.getBytes());
        }
        channel.close();
        connection.close();
        System.out.println("发送fanout消息结束");
    }
}
