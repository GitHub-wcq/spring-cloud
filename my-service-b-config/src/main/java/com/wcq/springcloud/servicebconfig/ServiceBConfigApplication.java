package com.wcq.springcloud.servicebconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceBConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBConfigApplication.class, args);
    }
}
