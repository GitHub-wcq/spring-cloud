package com.wcq.springcloud.consul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consul")
public class HelloController {
	
	@GetMapping("/get")
	public String getHello() {
		return "hello consul 2";
	}

}
