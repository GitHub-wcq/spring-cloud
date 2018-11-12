package com.wcq.springcloud.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    //java -jar eureka-0.0.1-SNAPSHOT.jar --server.port=9001 --eureka.instance.hostname=eureka-peer1 --eureka.client.service-url.defaultZone
    //java -jar eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer10001
    //java -jar eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer10002
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
