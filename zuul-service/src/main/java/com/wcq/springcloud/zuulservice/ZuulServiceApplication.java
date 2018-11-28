package com.wcq.springcloud.zuulservice;

import com.wcq.springcloud.zuulservice.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * 添加EnableZuulProxy注解开启zuul功能
 * https://blog.csdn.net/u011820505/article/details/79373594
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServiceApplication.class, args);
    }

    //将TokenFilter加入到请求拦截队列，在启动类中添加
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
