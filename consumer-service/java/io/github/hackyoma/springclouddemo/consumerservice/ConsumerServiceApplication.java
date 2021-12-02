package io.github.hackyoma.springclouddemo.consumerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ConsumerServiceApplication
 *
 * @author hackyo
 * @version 2018/8/22
 */
@SpringBootApplication(scanBasePackages = {"io.github.hackyoma.springclouddemo"})
@EnableDiscoveryClient
public class ConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerServiceApplication.class, args);
    }

}
