package com.wcq.message.queue.scrabbitmqconsumer.util;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RabbitmqTopicConsumerUtil {
    // 定义“用户”队列和“订单”队列名称
    private static final String QUEUE_NAME_USER = "log_module_user";
    private static final String QUEUE_NAME_ORDER = "log_module_order";

    // 定义日志级别队列名称
    private static final String QUEUE_NAME_INFO = "log_level_info";
    private static final String QUEUE_NAME_WARNING = "log_level_warning";
    private static final String QUEUE_NAME_ERROR = "log_level_error";
    public static void main(String[] args) {

        try {
            Connection connection = getConnection();
            Channel channel = connection.createChannel();
            // 限制每个consumer可以接收到的未被ACK的消息的最大数量
            channel.basicQos(20);
            // 收到消息时的回调
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("[Consumer]" + message);
                // 模拟具体的消息处理业务逻辑，耗时20ms处理完一个消息...
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                // 通知broker该消息已正确接收并处理完成
                channel.basicAck(deliveryTag, false);
            };
            //接受不同队列的消息
            channel.basicConsume(QUEUE_NAME_USER, false, deliverCallback, consumerTag -> {});
            channel.basicConsume(QUEUE_NAME_ORDER, false, deliverCallback, consumerTag -> {});
            channel.basicConsume(QUEUE_NAME_INFO, false, deliverCallback, consumerTag -> {});
            channel.basicConsume(QUEUE_NAME_WARNING, false, deliverCallback, consumerTag -> {});
            channel.basicConsume(QUEUE_NAME_ERROR, false, deliverCallback, consumerTag -> {});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/test-vhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory.newConnection(() ->{
            List<Address> list = new ArrayList<>();
            list.add(new Address("100.64.64.176",5672));
            return list;
        });
    }
}
