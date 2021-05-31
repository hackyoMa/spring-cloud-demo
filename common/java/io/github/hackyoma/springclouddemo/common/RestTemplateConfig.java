package io.github.hackyoma.springclouddemo.common;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * RestTemplateConfig
 *
 * @author hackyo
 * @version 2021/2/5
 */
@Component
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate getRestTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        return restTemplateBuilder.configure(new RestTemplate(new OkHttp3ClientHttpRequestFactory(client)));
    }

}
