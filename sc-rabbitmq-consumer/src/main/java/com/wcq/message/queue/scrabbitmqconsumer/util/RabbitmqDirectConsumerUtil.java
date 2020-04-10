package com.wcq.message.queue.scrabbitmqconsumer.util;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RabbitmqDirectConsumerUtil {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection(() ->{
            List list = new ArrayList();
            list.add(new Address("100.64.64.176",5672));
            return list;
        });
        String exchangeName = "direct-exchange";
        String routingKey1 = "direct-echg-rk-one";
        String routingKey2 = "direct-echg-rk-two";

        ConsumerThread consumerThread = new ConsumerThread(connection,exchangeName,routingKey1);
        Thread thread = new Thread(consumerThread);
        thread.start();
        ConsumerThread consumerThread1 = new ConsumerThread(connection,exchangeName,routingKey1);
        Thread thread1 = new Thread(consumerThread1);
        thread1.start();

        ConsumerThread consumerThread2 = new ConsumerThread(connection,exchangeName,routingKey2);
        Thread thread2 = new Thread(consumerThread2);
        thread2.start();

    }
    static class ConsumerThread implements Runnable{
        Connection connection;
        String exchangeName;
        String routingKey;
        public ConsumerThread(Connection connection,String exchangeName,String routingKey){
            this.connection = connection;
            this.exchangeName = exchangeName;
            this.routingKey = routingKey;
        }
        @Override
        public void run() {
            Channel channel = null;
            try {
                channel = connection.createChannel();channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
                //获取一个临时队列
                String queueName = channel.queueDeclare().getQueue();
                System.out.println("queueName : " + queueName);
                //队列与交换机绑定（参数为：队列名称；交换机名称；密匙-routingKey）
                channel.queueBind(queueName,exchangeName,routingKey);
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body)
                            throws IOException
                    {
                        super.handleDelivery(consumerTag, envelope, properties, body);
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().hashCode());
                        System.out.println(" direct 消费者接受消息 ： {" + new String(body,"UTF-8") + "}");
                    }
                };
                //声明队列中被消费掉的消息（参数为：队列名称；消息是否自动确认;consumer主体）
                channel.basicConsume(queueName,true,consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
