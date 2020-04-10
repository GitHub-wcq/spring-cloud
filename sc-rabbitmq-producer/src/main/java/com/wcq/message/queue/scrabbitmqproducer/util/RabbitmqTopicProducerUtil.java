package com.wcq.message.queue.scrabbitmqproducer.util;

import com.rabbitmq.client.*;
import org.omg.CORBA.TRANSACTION_MODE;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class RabbitmqTopicProducerUtil {

    // 定义 exchange 名称
    private static final String EXCHANGE_NAME = "topic_logs";

    // 定义“用户”队列和“订单”队列名称
    private static final String QUEUE_NAME_USER = "log_module_user";
    private static final String QUEUE_NAME_ORDER = "log_module_order";

    // 定义日志级别队列名称
    private static final String QUEUE_NAME_INFO = "log_level_info";
    private static final String QUEUE_NAME_WARNING = "log_level_warning";
    private static final String QUEUE_NAME_ERROR = "log_level_error";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPassword("guest");
        factory.setUsername("guest");

        Connection connection = factory.newConnection(() ->{
            List<Address> list = new ArrayList<>();
            list.add(new Address("100.64.64.176",5672));
            return list;
        });
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC,true);

        //Lazy mode队列，消息将保存到磁盘，确保消息不会丢失
        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-queue-mode","lazy");
        //声明 user队列和 order队列
        channel.queueDeclare(QUEUE_NAME_USER, true, false, false, arguments);
        channel.queueDeclare(QUEUE_NAME_ORDER, true, false, false, arguments);

        //声明日志级别队列
        channel.queueDeclare(QUEUE_NAME_INFO, true, false, false, arguments);
        channel.queueDeclare(QUEUE_NAME_WARNING, true, false, false, arguments);
        channel.queueDeclare(QUEUE_NAME_ERROR, true, false, false, arguments);

        // 将队列绑定到exchange
        channel.queueBind(QUEUE_NAME_USER,EXCHANGE_NAME , "user.*");
        channel.queueBind(QUEUE_NAME_ORDER,EXCHANGE_NAME, "order.*");

        channel.queueBind(QUEUE_NAME_INFO,EXCHANGE_NAME,"*.info");
        channel.queueBind(QUEUE_NAME_WARNING, EXCHANGE_NAME, "*.warning");
        channel.queueBind(QUEUE_NAME_ERROR, EXCHANGE_NAME, "*.error");

        //在此channel启用发布者确认。
        channel.confirmSelect();

        while (true){
            for (int i = 0; i < 20; i++){
                String routingKey = getRandomRoutingKey();
                String content = getRandomContent(routingKey);
                channel.basicPublish(EXCHANGE_NAME, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN,content.getBytes(StandardCharsets.UTF_8));
                System.out.println("[ producer ]  " + content);
            }
            boolean confirmed = channel.waitForConfirms();
            if (confirmed) {
                System.out.println("以上消息成功投递并确认");
            } else {
                System.out.println("以上消息已经投递未确认");
            }
            Thread.sleep(1000);
        }
    }


    private static final String[] MODULES = new String[] { "user", "order" };
    private static final String[] LEVELS = new String[] { "info", "warning", "error" };
    private static String getRandomRoutingKey() {
        String module = getRandomElementInArray(MODULES);
        String level = getRandomElementInArray(LEVELS);
        return module + "." + level;
    }
    private static final Random RANDOM = new Random();

    private static String getRandomElementInArray(String[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    private static String getRandomContent(String routingKey) {
        return routingKey + " : " + LocalDateTime.now().toString();
    }
}
