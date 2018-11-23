package com.wcq.springcloud.servicebhystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class HystrixService {

    @HystrixCommand(fallbackMethod = "getHystrixException")
    public String getHystrix(String name){
        throw new RuntimeException();
        //return "hystrix ===> " + name;
    }

    public String getHystrixException(String name){
        return "hystrix  exception : " + name;
    }

}
