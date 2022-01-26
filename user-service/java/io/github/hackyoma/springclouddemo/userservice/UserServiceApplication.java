package io.github.hackyoma.springclouddemo.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * UserServiceApplication
 *
 * @author hackyo
 * @version 2018/8/22
 */
@SpringBootApplication(scanBasePackages = {"io.github.hackyoma.springclouddemo"})
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
