package com.wcq.springcloud.servicebfeign.fallback;

import com.wcq.springcloud.servicebfeign.service.FeignService;
import org.springframework.stereotype.Component;

/**
 * fallback方法
 */
@Component
public class FeignServiceFallBack implements FeignService {
    @Override
    public String getServiceConfigFooVersion() {
        return "服务报错";
    }
}
