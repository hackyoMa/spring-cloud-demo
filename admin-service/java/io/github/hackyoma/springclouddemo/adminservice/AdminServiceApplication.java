package io.github.hackyoma.springclouddemo.adminservice;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * AdminServiceApplication
 *
 * @author hackyo
 * @version 2018/8/22
 */
@SpringBootApplication(scanBasePackages = {"io.github.hackyoma.springclouddemo"})
@EnableDiscoveryClient
@EnableAdminServer
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

}
