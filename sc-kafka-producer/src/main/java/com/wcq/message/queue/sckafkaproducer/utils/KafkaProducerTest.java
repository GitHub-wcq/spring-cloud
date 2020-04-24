package com.wcq.message.queue.sckafkaproducer.utils;

import com.wcq.message.queue.sckafkaproducer.config.KafkaConfig;
import org.apache.kafka.clients.producer.*;

import java.util.concurrent.Future;

public class KafkaProducerTest {

    public static void main(String[] args) {
        Producer producer = new KafkaProducer(KafkaConfig.getProperties());
        try {
            for (int i = 0; i < 20; i++){
                String msg = "KafkaProducerTest ==> sendMsg : " + Integer.valueOf(i << 1).toString() + " -- " + System.currentTimeMillis();
                System.out.println(msg);
                Future<RecordMetadata> future = producer.send(new ProducerRecord("test", msg), new Callback() {
                    //如果需要可以通过callback设置回调函数进行进一步操作
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        System.out.println("topic : " + metadata.topic());
                        System.out.println("partition : " + metadata.partition());
                        System.out.println("offset : " + metadata.offset());
                        System.out.println("=== callback === ");
                    }
                });
                //如果只需要recordMetadata的信息，可通过producer.send().get()获取。
                RecordMetadata recordMetadata = future.get();
                //The offset of the record in the topic/partition.
                Long offset = recordMetadata.offset();
                System.out.println( "future.recodMetadata.offset : " + offset);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

}
