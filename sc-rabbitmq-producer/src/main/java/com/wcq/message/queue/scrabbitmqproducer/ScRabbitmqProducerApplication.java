package com.wcq.message.queue.scrabbitmqproducer;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class ScRabbitmqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScRabbitmqProducerApplication.class, args);
    }

    @Bean
    @Scope(value = "prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }
}
