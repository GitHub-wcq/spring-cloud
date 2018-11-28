package com.wcq.springcloud.zuulservice.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 路由熔断
 *
 * 当我们的后端服务出现异常的时候，我们不希望将异常抛出给最外层，期望服务可以自动进行一降级。
 * Zuul给我们提供了这样的支持。当某个服务出现异常时，直接返回我们预设的信息。
 *
 * 通过自定义的fallback方法，并且将其指定给某个route来实现该route访问出问题的熔断处理。
 * 主要继承FallbackProvider接口来实现，FallbackProvider默认有两个方法，一个用来指明熔断拦截哪个服务，一个定制返回内容。
 */
@Component
public class BConfigFallback implements FallbackProvider {

    private final Logger logger = LoggerFactory.getLogger(BConfigFallback.class);

    //指定要处理的 service。
    @Override
    public String getRoute() {
        return "service-b-config";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause != null && cause.getCause() != null) {
            String reason = cause.getCause().getMessage();
            logger.info("Excption {}",reason);
        }
        //HttpStatus可以写500，也可以写200.代表当调用service-b-config服务的时候，如果异常，当前请求返回的状态值
//        return response(HttpStatus.INTERNAL_SERVER_ERROR);
        return response(HttpStatus.OK);
    }
    private ClientHttpResponse response(final HttpStatus status) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                //response的内容
                return new ByteArrayInputStream("The service is unavailable.".getBytes());
            }

            /**
             * Return the headers of this message.
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
