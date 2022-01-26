package io.github.hackyoma.springclouddemo.servicecommon.filter;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

/**
 * SentinelFilterConfig
 *
 * @author hackyo
 * @version 2022/1/11
 */
@Configuration
public class SentinelFilterConfig {

    @Value("${spring.application.name}")
    private String projectName;
    @Value("${spring.cloud.sentinel.transport.dashboard}")
    private String dashboardServer;
    @Value("${spring.cloud.sentinel.transport.port}")
    private String apiPort;

    @PostConstruct
    private void init() {
        if (StringUtils.isNotBlank(projectName)) {
            System.setProperty("project.name", projectName);
        }
        if (StringUtils.isNotBlank(dashboardServer)) {
            System.setProperty("csp.sentinel.dashboard.server", dashboardServer);
        }
        if (StringUtils.isNotBlank(apiPort)) {
            System.setProperty("csp.sentinel.api.port", apiPort);
        }
    }

    @Bean
    public FilterRegistrationBean<Filter> sentinelFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(900);
        return registration;
    }

}
