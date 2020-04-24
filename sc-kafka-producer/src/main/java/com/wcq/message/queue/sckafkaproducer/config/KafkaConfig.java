package com.wcq.message.queue.sckafkaproducer.config;

import java.util.Properties;

public class KafkaConfig {

    public static Properties getProperties(){
        Properties properties = new Properties();
        /**
         * bootstrap.servers:用于初始化时建立链接到kafka集群，以host:port形式，多个以逗号分隔host1:port1,host2:port2；
         */
        properties.put("bootstrap.servers","100.64.64.170:9092,100.64.64.195:9092,100.64.64.186:9092,100.64.64.191:9092,100.64.64.193:9092");
        /**
         * acks:生产者需要server端在接收到消息后，进行反馈确认的尺度，主要用于消息的可靠性传输；
         *
         * acks=0表示生产者不需要来自server的确认；
         *
         * acks=1表示server端将消息保存后即可发送ack，而不必等到其他follower角色的都收到了该消息；
         * producer只要收到一个分区副本成功写入的通知就认为推送消息成功了。
         * 需要注意，这个副本必须是leader副本。只有leader副本成功写入了，producer才会认为消息发送成功。
         *
         * acks=all(or acks=-1)意味着server端将等待所有的副本都被接收后才发送确认。
         */
        properties.put("acks","all");
        /**
         * retries:生产者发送失败后，重试的次数
         */
        properties.put("retries",0);
        /**
         * batch.size:当多条消息发送到同一个partition时，
         * 该值控制生产者批量发送消息的大小，批量发送可以减少生产者到服务端的请求数，有助于提高客户端和服务端的性能。
         */
        properties.put("batch.size",16384);
        /**
         * linger.ms:默认情况下缓冲区的消息会被立即发送到服务端，即使缓冲区的空间并没有被用完。
         * 可以将该值设置为大于0的值，这样发送者将等待一段时间后，再向服务端发送请求，以实现每次请求可以尽可能多的发送批量消息。
         * batch.size 和 linger.ms 是两种实现让客户端每次请求尽可能多的发送消息的机制，它们可以并存使用，并不冲突。
         */
        properties.put("linger.ms",1);
        /**
         * buffer.memory:生产者缓冲区的大小，保存的是还未来得及发送到server端的消息，
         * 如果生产者的发送速度大于消息被提交到server端的速度，该缓冲区将被耗尽。
         */
        properties.put("buffer.memory",33554432);
        /**
         * key.serializer,value.serializer说明了使用何种序列化方式将用户提供的key和vaule值序列化成字节。
         */
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return properties;
    }

}
