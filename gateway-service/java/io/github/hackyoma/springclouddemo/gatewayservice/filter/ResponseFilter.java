package io.github.hackyoma.springclouddemo.gatewayservice.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import io.github.hackyoma.springclouddemo.gatewayservice.entity.Result;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.WebClientWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * ResponseFilter
 *
 * @author hackyo
 * @version 2022/1/24
 */
@Component
public class ResponseFilter implements GlobalFilter, Ordered {

    private final static String ERROR_MESSAGE_KEY = "message";

    private static byte[] formatResponseBody(HttpStatus httpStatus, HttpHeaders headers, byte[] content) {
        Result result = new Result();
        result.setCode(httpStatus.value());
        result.setMessage(httpStatus.getReasonPhrase());
        if (content != null) {
            if (httpStatus.is2xxSuccessful()) {
                if (JSONValidator.fromUtf8(content).validate()) {
                    result.setData(JSON.parse(content));
                } else {
                    result.setData(new String(content, StandardCharsets.UTF_8));
                }
            } else {
                if (JSONValidator.fromUtf8(content).validate()) {
                    JSONObject message = JSONObject.parseObject(content, JSONObject.class);
                    if (message.containsKey(ERROR_MESSAGE_KEY)) {
                        result.setMessage(message.getString(ERROR_MESSAGE_KEY));
                    }
                }
            }
        }
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        headers.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return JSONObject.toJSONBytes(result);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(response) {
            @Override
            @NonNull
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                HttpStatus httpStatus = super.getStatusCode();
                if (httpStatus != null && body instanceof Flux) {
                    HttpHeaders headers = super.getDelegate().getHeaders();
                    Publisher<? extends DataBuffer> newBody;
                    if (headers.getContentLength() == 0) {
                        newBody = Flux.just(bufferFactory.wrap(formatResponseBody(httpStatus, headers, null)));
                    } else {
                        newBody = Flux.from(body).buffer().map(dataBuffers -> {
                            DataBuffer dataBuffer = bufferFactory.join(dataBuffers);
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            DataBufferUtils.release(dataBuffer);
                            return bufferFactory.wrap(formatResponseBody(httpStatus, headers, content));
                        });
                    }
                    return super.writeWith(newBody);
                }
                return super.writeWith(body);
            }

            @Override
            @NonNull
            public Mono<Void> writeAndFlushWith(@NonNull Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return this.writeWith(Flux.from(body).flatMapSequential(mapper -> mapper));
            }
        };
        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }

    @Override
    public int getOrder() {
        return WebClientWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;
    }

}
