package com.wcq.springcloud.consul.fegin.fallback;

import org.springframework.stereotype.Component;

import com.wcq.springcloud.consul.fegin.HelloService;

@Component
public class HelloServiceFallback implements HelloService {

	@Override
	public String getHello() {
		return "service-producer服务调用失败";
	}

}
