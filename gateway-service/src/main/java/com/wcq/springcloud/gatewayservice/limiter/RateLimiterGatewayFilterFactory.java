package com.wcq.springcloud.gatewayservice.limiter;

import com.alibaba.fastjson.JSON;
import com.wcq.springcloud.gatewayservice.constents.Response;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RateLimiterGatewayFilterFactory.Config> {

    public static final String KEY_RESOLVER_KEY = "keyResolver";

    private final RateLimiter defaultRateLimiter;
    private final KeyResolver defaultKeyResolver;

    public RateLimiterGatewayFilterFactory(RateLimiter rateLimiter, KeyResolver keyResolver) {
        super(Config.class);
        this.defaultRateLimiter = rateLimiter;
        this.defaultKeyResolver = keyResolver;
    }

    /**
     * 我们重新写一个过滤器（基本和官方一致），只是将官方的返回报文改了。
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        KeyResolver resolver = (config.keyResolver == null) ? defaultKeyResolver : config.keyResolver;
        RateLimiter<Object> limiter = (config.rateLimiter == null) ? defaultRateLimiter : config.rateLimiter;
        return ((exchange, chain) -> {
            Route route = (Route) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            return resolver.resolve(exchange).flatMap(key -> limiter.isAllowed(route.getId(),key).flatMap(response -> {
                for (Map.Entry<String,String> header : response.getHeaders().entrySet()) {
                    exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
                }
                if (response.isAllowed()){
                    return chain.filter(exchange);
                }
                ServerHttpResponse rs = exchange.getResponse();
                Response data = new Response();
                data.setCode(101);
                data.setMessage("访问过快");
                byte[] datas = JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = rs.bufferFactory().wrap(datas);
                rs.setStatusCode(HttpStatus.UNAUTHORIZED);
                rs.getHeaders().add("Content-Type","application/json;charset=UTF-8");
                return rs.writeWith(Mono.just(buffer));
            }));
        });
    }

    public static class Config {
        private KeyResolver keyResolver;
        private RateLimiter rateLimiter;
        private HttpStatus statusCode = HttpStatus.TOO_MANY_REQUESTS;

        public KeyResolver getKeyResolver() {
            return keyResolver;
        }

        public void setKeyResolver(KeyResolver keyResolver) {
            this.keyResolver = keyResolver;
        }

        public RateLimiter getRateLimiter() {
            return rateLimiter;
        }

        public void setRateLimiter(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
        }

        public HttpStatus getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }
    }
}
