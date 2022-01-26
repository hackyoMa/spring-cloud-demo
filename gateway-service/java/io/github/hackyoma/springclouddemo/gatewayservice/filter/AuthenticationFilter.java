package io.github.hackyoma.springclouddemo.gatewayservice.filter;

import io.github.hackyoma.springclouddemo.common.JwtTokenUtil;
import io.github.hackyoma.springclouddemo.common.RequestUtil;
import io.github.hackyoma.springclouddemo.common.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * AuthenticationFilter
 *
 * @author hackyo
 * @version 2018/8/22
 */
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final static AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private final SecurityProperties securityProperties;

    @Autowired
    public AuthenticationFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        Route gatewayRoute = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String serviceId = gatewayRoute.getUri().getHost();
        String path = request.getPath().value();
        HttpMethod method = request.getMethod();
        if (method == null) {
            response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            return response.setComplete();
        }

        List<String> whitelist = securityProperties.getAllWhitelist(serviceId, method.name(), false);
        if (whitelist.stream().anyMatch(wl -> PATH_MATCHER.match(wl, path))) {
            return chain.filter(exchange);
        }

        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(token) && JwtTokenUtil.validateToken(token)) {
            request = request.mutate().header(RequestUtil.USER_ID, JwtTokenUtil.getUserIdFromToken(token)).build();
            exchange = exchange.mutate().request(request).build();
            return chain.filter(exchange);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 800;
    }

}
