package com.wcq.springcloud.servicebfeign.service;

import com.wcq.springcloud.servicebfeign.fallback.FeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 接口调用，name代表serviceid
 */
@FeignClient(name = "service-b-config", fallback = FeignServiceFallBack.class)
public interface FeignService {

    @GetMapping("/foo/getFooVersion")
    String getServiceConfigFooVersion();

}
