package com.wcq.springcloud.gatewayservice.limiter;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.List;

public class CustomRedisRateLimiter extends RedisRateLimiter {
    public CustomRedisRateLimiter(ReactiveRedisTemplate<String, String> redisTemplate, RedisScript<List<Long>> script, Validator validator) {
        super(redisTemplate, script, validator);
    }

    public CustomRedisRateLimiter(int defaultReplenishRate, int defaultBurstCapacity) {
        super(defaultReplenishRate, defaultBurstCapacity);
    }


    @Override
    public Mono<Response> isAllowed(String routeId, String id) {

    }

}
