package com.wcq.message.queue.sckafkaconsumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class KafkaConfig {

    public static Properties getProperties(){
        Properties properties = new Properties();
        /**
         * bootstrap.servers:用于初始化时建立链接到kafka集群，以host:port形式，多个以逗号分隔host1:port1,host2:port2；
         */
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"100.64.64.170:9092,100.64.64.195:9092,100.64.64.186:9092,100.64.64.191:9092,100.64.64.193:9092");
        /**
         * group.id 表示该consumer想要加入到哪个group中。默认值是 ""。
         */
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test-group-1");
        /**
         * enable.auto.commit
         * Consumer 在commit offset时有两种模式：自动提交，手动提交。自动提交：是Kafka Consumer会在后台周期性的去commit。
         */
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        /**
         * auto.commit.interval.ms
         * 自动提交间隔。范围：[0,Integer.MAX]，默认值是 5000 （5 s）
         */
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,1000);
        /**
         * auto.offset.reset  默认值是latest。
         * 告诉Kafka Broker在发现kafka在没有初始offset，或者当前的offset是一个不存在的值（如果一个record被删除，就肯定不存在了）时，该如何处理。它有4种处理方式：
         * 1） earliest：自动重置到最早的offset。
         * 2） latest：看上去重置到最晚的offset。
         * 3） none：如果边更早的offset也没有的话，就抛出异常给consumer，告诉consumer在整个consumer group中都没有发现有这样的offset。
         * 4） 如果不是上述3种，只抛出异常给consumer。
         */
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        /**
         * session.timeout.ms 默认值是：10000 （10 s）
         * Consumer session 过期时间。这个值必须设置在broker configuration中的group.min.session.timeout.ms 与 group.max.session.timeout.ms之间。
         */
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,30000);
        /**
         * 一次性获取的数量
         */
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"5");

        /**
         * key.serializer,value.serializer说明了使用何种序列化方式将用户提供的key和vaule值序列化成字节。
         */
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }

}
