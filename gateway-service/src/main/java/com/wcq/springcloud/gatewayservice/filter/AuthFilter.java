package com.wcq.springcloud.gatewayservice.filter;

import com.alibaba.fastjson.JSON;
import com.wcq.springcloud.gatewayservice.constents.Response;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 认证过滤器
 */
@Component
public class AuthFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //从headers中根据token的key名称获取token的值
        String token = exchange.getRequest().getHeaders().getFirst("token");
        // 获取请求的的url，可以根据该url进行白名单验证或者进行分流。
        String requestURL = exchange.getRequest().getPath().value();
        System.out.println(requestURL);

        if ("token".equals(token)){
            return chain.filter(exchange);
        }
        ServerHttpResponse serverHttpResponse = exchange.getResponse();

        Response response = new Response();
        response.setCode(401);
        response.setMessage("非法请求");

        byte[] datas = JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(datas);
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        serverHttpResponse.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }
}
