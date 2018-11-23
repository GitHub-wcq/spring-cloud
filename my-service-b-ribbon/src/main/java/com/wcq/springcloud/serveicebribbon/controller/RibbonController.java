package com.wcq.springcloud.serveicebribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {

    @Value("${envvvv}")
    String envvvv;
    @Value("${service.name}")
    String serviceName;
    @Value("${server.port}")
    String port;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/getFoo")
    public String getFoo(){
        System.out.println("service-b-ribbon 调用service-b-config服务");
        return restTemplate.getForObject("http://service-b-config/foo/getFooVersion",String.class);
    }

    @GetMapping("/getEnvvvv")
    public String getEnvvvv(){
        return envvvv + " ; from " + port;
    }
    @GetMapping("/getServiceName")
    public String getServiceName(){
        return serviceName + " ; from " + port;
    }


}
