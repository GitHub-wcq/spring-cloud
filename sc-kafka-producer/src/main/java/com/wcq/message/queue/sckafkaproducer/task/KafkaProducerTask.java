package com.wcq.message.queue.sckafkaproducer.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
public class KafkaProducerTask {

    @Scheduled(cron = "*/1 * * * * ?")
    public void sendMsg(){
        String msg = "KafkaProducerTask == > sendMsg : " + System.currentTimeMillis();
        log.info(msg);
    }
}
