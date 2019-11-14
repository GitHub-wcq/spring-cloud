package com.wcq.springcloud.consul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcq.springcloud.consul.fegin.HelloService;

@RestController
@RequestMapping("/consumer")
public class ServiceController {
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/services")
	public Object services() {
		return discoveryClient.getInstances("service-producer");
	}
	@GetMapping("/discover")
	public Object discover() {
		return loadBalancerClient.choose("service-producer").getUri().toString();
	}
	
	@Autowired
	private HelloService helloService;
	@GetMapping("/getHello")
	public String getHello() {
		return helloService.getHello();
	}
}
