package com.wcq.springcloud.servicebconfig.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
@RefreshScope
public class FooController {

    private final static Logger logger = LoggerFactory.getLogger(FooController.class);

    @Value("${foo.version}")
    String fooVersion;
    @Value("${envvvv: dev}")
    String envvvv;
    @Value("${server.port}")
    String port;

    @GetMapping("/getFooVersion")
    public String getFooVersion(){
        return fooVersion + " ; from " + port;
    }

    @GetMapping("/getEnvvvv")
    public String getEnvvvv(){
        return envvvv + " ; from " + port;
    }

    /**
     * 测试zuul重试机制
     * @param name
     * @return
     */
    @GetMapping("/zuulRetryTest")
    public String zuulRetryTest(@RequestParam String name){
        logger.info("request two name is "+name);
        try {
            Thread.sleep(1000000);
        } catch (Exception e){
            logger.error(" hello two error",e);
        }
        return "hello "+name+"，this is two messge";
    }

}
