package com.wcq.springcloud.gatewayservice.filter;

import com.alibaba.fastjson.JSON;
import com.wcq.springcloud.gatewayservice.constents.Response;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * 网关过滤器
 */
@Component
public class WrapperResponseFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();

        /**
         * 修改返回报文内容
         */
        ServerHttpResponseDecorator decoratorResponse = new ServerHttpResponseDecorator(originalResponse){
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放内存
                        DataBufferUtils.release(dataBuffer);
                        //rs即为response的内容，可以对rs进行修改
                        String rs = new String(content, Charset.forName("UTF-8"));

                        Response response = new Response();
                        response.setMessage("请求成功");
                        response.setData(rs);


                        byte[] newRs = JSON.toJSONString(response).getBytes(Charset.forName("UTF-8"));
                        //如果不重新设置长度则收不到消息
                        originalResponse.getHeaders().setContentLength(newRs.length);
                        return bufferFactory.wrap(newRs);
                    }));
                }
                // if body is not a flux, never got there
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratorResponse).build());
    }

    @Override
    public int getOrder() {
        // -1 is response write filter, must be called before that
        return -2;
    }
}
