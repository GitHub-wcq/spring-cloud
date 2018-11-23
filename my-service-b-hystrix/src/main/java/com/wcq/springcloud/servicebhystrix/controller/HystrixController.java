package com.wcq.springcloud.servicebhystrix.controller;

import com.wcq.springcloud.servicebhystrix.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hystrix")
@RestController
public class HystrixController {

    @Value("${service.name}")
    String servicName;

    @Autowired
    HystrixService hystrixService;

    @GetMapping("/getHystrix")
    public String getHystrix(String name){
        return hystrixService.getHystrix(name) + "; service.name : " + servicName;
    }

}
