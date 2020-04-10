package com.wcq.message.queue.scrabbitmqconsumer.util;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RabbitmqFanoutConsumerUtil {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("haier");
        factory.setPassword("Haier");
        Connection connection = factory.newConnection(() -> {
            List<Address> list = new ArrayList<>();
            list.add(new Address("100.64.64.176",5672));
            return list;
        });
        String exchangeName = "fanout-exchange";
        FanoutThread fanoutThread = new FanoutThread(connection,exchangeName);
        fanoutThread.start();

        FanoutThread fanoutThread1 = new FanoutThread(connection,exchangeName);
        fanoutThread1.start();

        FanoutThread fanoutThread2 = new FanoutThread(connection,exchangeName);
        fanoutThread2.start();
    }

    static class FanoutThread extends Thread {
        private Connection connection;
        private String exchangeName;
        public FanoutThread(Connection connection,String exchangeName){
            this.connection = connection;
            this.exchangeName = exchangeName;
        }
        @Override
        public void run(){
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.exchangeDeclare("fanout-exchange", BuiltinExchangeType.FANOUT);
                String queueName = channel.queueDeclare().getQueue();
                System.out.println("queueName : " + queueName);
                channel.queueBind(queueName,exchangeName,"");
                Consumer consumer = new DefaultConsumer(channel){
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
                        System.out.println(" fanout 消费者接受消息 ： {" + new String(body,"UTF-8") + "}");
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
