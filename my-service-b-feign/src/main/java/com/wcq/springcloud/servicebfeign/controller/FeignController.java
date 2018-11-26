package com.wcq.springcloud.servicebfeign.controller;

import com.wcq.springcloud.servicebfeign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    FeignService feignService;

    @GetMapping("/getFeginFoo")
    public String getFeginFoo(){
        return feignService.getServiceConfigFooVersion();
    }

}
