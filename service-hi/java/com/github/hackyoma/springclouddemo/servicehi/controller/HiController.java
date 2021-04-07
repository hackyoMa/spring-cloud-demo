package com.github.hackyoma.springclouddemo.servicehi.controller;

import com.github.hackyoma.springclouddemo.servicehi.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * HiController
 *
 * @author hackyo
 * @version 2018/8/22
 */
@RestController
public class HiController {

    private final HiService hiService;

    @Autowired
    public HiController(HiService hiService) {
        this.hiService = hiService;
    }

    @GetMapping("/hi")
    public String hi(@RequestParam("name") String name) {
        return hiService.hi(name);
    }

}
