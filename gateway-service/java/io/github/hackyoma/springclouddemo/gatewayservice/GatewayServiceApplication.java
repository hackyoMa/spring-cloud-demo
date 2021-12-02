package io.github.hackyoma.springclouddemo.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * GatewayServiceApplication
 *
 * @author hackyo
 * @version 2018/8/22
 */
@SpringBootApplication(scanBasePackages = {"io.github.hackyoma.springclouddemo"})
@EnableDiscoveryClient
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

}
