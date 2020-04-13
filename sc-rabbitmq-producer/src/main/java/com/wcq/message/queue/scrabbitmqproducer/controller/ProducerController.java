package com.wcq.message.queue.scrabbitmqproducer.controller;

import com.wcq.message.queue.scrabbitmqproducer.service.RabbitDirectProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private RabbitDirectProducerService rabbitDirectProducerService;

    @GetMapping("/direct")
    public String directSendMsg(String msg){
        rabbitDirectProducerService.send(msg);
        return "success";
    }

}
