package com.wcq.message.queue.sckafkaconsumer.utils;

import com.wcq.message.queue.sckafkaconsumer.config.KafkaConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerTest {
    public static void main(String[] args) {
        Consumer consumer = new KafkaConsumer(KafkaConfig.getProperties());
//        //1. 订阅topic消费
//        consumer.subscribe(Arrays.asList("test"));

//        //2. 指定偏移量消费
//        TopicPartition tp = new TopicPartition("test",0);
//        //订阅指定分区
//        consumer.assign(Collections.singleton(tp));
//        //指定偏移量为4
//        consumer.seek(tp,4L);

        //3. 指定时间戳消费
        TopicPartition topicPartition = new TopicPartition("test",0);
        //指定订阅分区
        consumer.assign(Collections.singleton(topicPartition));
        Map<TopicPartition, Long> tpTime = new HashMap<>();
        //指定时间戳
        tpTime.put(topicPartition,1587716051400L);
        Map<TopicPartition, OffsetAndTimestamp> tpOffsetAndTime = consumer.offsetsForTimes(tpTime);
        //获取偏移量
        long offset = tpOffsetAndTime.get(topicPartition).offset();
        //指定偏移量
        consumer.seek(topicPartition,offset);

        while (true){
            //consumer.poll(Duration.ofSeconds(3))中的数字代表了poll的阻塞时间，在消费者缓冲区没有数据时会发生阻塞。
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofSeconds(3));
            System.out.println("consumer ===== start =====");
            records.forEach(record -> {
                System.out.println("offset ： " + record.offset());
                System.out.println("value : " + record.value());
                System.out.println("key : " + record.key());
                System.out.println("partition : " + record.partition());
                System.out.println("topic : " + record.topic());
                System.out.println("===========================================================");
            });
            try {
                //enable.auto.commit设置为false时
                //手动提交，该方法会将poll后的最大offset提交到kafka.
                //手动提交缺点：在broker回应之前，应用程序会一直阻塞，这样会限制应用的吞吐量。我们可以通过降低提交频率提示吞吐量，但如果发生了再均衡，会增加重复消息的数量。
                consumer.commitSync();
                //异步提交
//                //commitSync()在未成功的时候会一直重试，commitAsync()不会重试。
//                //这时候如果提交offset=2000因为网络问题出现延迟，另一个消费者提交了3000后，2000才提交成功，这个时候发生了再均衡，会出现重复消费。
//                //可以通过commitAsync(OffsetCommitCallback callback)  callback进一步处理
//                consumer.commitAsync();
            }catch (CommitFailedException e){
                System.err.println(e);
            }
        }
    }
}
