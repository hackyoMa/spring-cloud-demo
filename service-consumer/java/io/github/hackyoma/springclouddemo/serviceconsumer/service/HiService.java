package io.github.hackyoma.springclouddemo.serviceconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * HiService
 *
 * @author hackyo
 * @version 2018/8/22
 */
@Service
public class HiService {

    private final RestTemplate restTemplate;

    @Autowired
    public HiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String hi(String name) {
        Map<String, String> params = new HashMap<>(1);
        params.put("name", name);
        return restTemplate.getForEntity("http://service-hi/hi?name={name}", String.class, params).getBody();
    }

}
