package io.github.hackyoma.springclouddemo.hiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * HiServiceApplication
 *
 * @author hackyo
 * @version 2018/8/22
 */
@SpringBootApplication(scanBasePackages = {"io.github.hackyoma.springclouddemo"})
@EnableDiscoveryClient
public class HiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiServiceApplication.class, args);
    }

}
