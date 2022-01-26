package io.github.hackyoma.springclouddemo.gatewayservice.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * CorsWebFilter
 *
 * @author hackyo
 * @version 2022/1/24
 */
@Component
public class CorsWebFilter implements WebFilter {

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders requestHeaders = request.getHeaders();
        HttpHeaders responseHeaders = response.getHeaders();
        responseHeaders.setAccessControlAllowOrigin(requestHeaders.getOrigin());
        responseHeaders.setAccessControlAllowMethods(Collections.singletonList(request.getMethod()));
        List<String> accessControlRequestHeaders = requestHeaders.getAccessControlRequestHeaders();
        if (accessControlRequestHeaders.isEmpty()) {
            accessControlRequestHeaders = Collections.singletonList("*");
        }
        responseHeaders.setAccessControlAllowHeaders(accessControlRequestHeaders);
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

}
