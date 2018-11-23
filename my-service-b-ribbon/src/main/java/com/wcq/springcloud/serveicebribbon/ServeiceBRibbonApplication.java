package com.wcq.springcloud.serveicebribbon;

import com.netflix.loadbalancer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class ServeiceBRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServeiceBRibbonApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 自定义配置ribbon负载均衡算法
     * @return
     */
    @Bean
    public IRule iRule(){
//        return new RoundRobinRule();  //轮询算法
//        return new RetryRule();
        return new RandomRule();   //随机算法
    }

}
