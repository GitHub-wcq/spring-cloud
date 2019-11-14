package com.wcq.springcloud.consul.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.wcq.springcloud.consul.fegin.fallback.HelloServiceFallback;

@FeignClient(name = "service-producer", fallback = HelloServiceFallback.class)
public interface HelloService {

	@GetMapping("/consul/get")
	String getHello();
	
}
