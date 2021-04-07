package com.github.hackyoma.springclouddemo.servicehi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * HiService
 *
 * @author 马昊宇
 * @version 2020/7/23
 */
@Service
public class HiService {

    @Value("${server.port}")
    private String port;

    public String hi(@RequestParam("name") String name) {
        return "hi " + name + ", i am from port:" + port;
    }

}
