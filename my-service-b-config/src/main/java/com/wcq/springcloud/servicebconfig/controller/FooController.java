package com.wcq.springcloud.servicebconfig.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
@RefreshScope
public class FooController {

    @Value("${foo.version}")
    String fooVersion;
    @Value("${envvvv: dev}")
    String envvvv;

    @GetMapping("/getFooVersion")
    public String getFooVersion(){
        return fooVersion;
    }

    @GetMapping("/getEnvvvv")
    public String getEnvvvv(){
        return envvvv;
    }

}
