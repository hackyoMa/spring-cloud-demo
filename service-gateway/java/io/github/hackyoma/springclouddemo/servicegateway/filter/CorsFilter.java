package io.github.hackyoma.springclouddemo.servicegateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * CorsFilter
 *
 * @author hackyo
 * @version 2020/7/24
 */
@Component
public class CorsFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.setAccessControlAllowOrigin(headers.getOrigin());
        List<String> accessControlRequestHeaders = headers.getAccessControlRequestHeaders();
        if (accessControlRequestHeaders.isEmpty()) {
            headers.set("Access-Control-Allow-Headers", "*");
        } else {
            headers.setAccessControlAllowHeaders(accessControlRequestHeaders);
        }
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -10;
    }

}
