package com.wcq.springcloud.gatewayservice.limiter;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CustomKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "customKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(getKey(exchange));
    }

    private String getKey(ServerWebExchange exchange) {
        LimitKey limitKey = new LimitKey();
        limitKey.setApi(exchange.getRequest().getPath().toString());
        limitKey.setBiz(exchange.getRequest().getQueryParams().getFirst("biz"));
        return JSON.toJSONString(limitKey);
    }
}
class LimitKey {
    private String api;
    private String biz;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }
}
