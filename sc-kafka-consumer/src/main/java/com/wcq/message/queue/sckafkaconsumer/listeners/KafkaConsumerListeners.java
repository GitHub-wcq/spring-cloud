package com.wcq.message.queue.sckafkaconsumer.listeners;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class KafkaConsumerListeners {

    @KafkaListener(id = "kafka-consumer-client-1", groupId = "test-group-1", topics = "test",concurrency = "2")
    public void consumeMsg(ConsumerRecord<String,Object> record,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           Consumer consumer, Acknowledgment ack){
        log.info("spring-kafka-consumer == > topic = {}, content = {}", topic, record.value());
        log.info("consumer content = {}", consumer);
        ack.acknowledge();
        /**
         * 手工异步提交：
         * consumer.commitSync();
         * 手工同步提交：
         * consumer.commitAsync();
         */
        consumer.commitAsync(new OffsetCommitCallback(){

            @Override
            public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                log.info(offsets.toString());
            }
        });

    }

}
