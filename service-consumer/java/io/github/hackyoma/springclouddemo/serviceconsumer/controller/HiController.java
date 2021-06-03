package io.github.hackyoma.springclouddemo.serviceconsumer.controller;

import io.github.hackyoma.springclouddemo.serviceconsumer.service.HiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "测试类")
@RestController
public class HiController {

    private final HiService hiService;

    @Autowired
    public HiController(HiService hiService) {
        this.hiService = hiService;
    }

    @ApiOperation("测试接口")
    @GetMapping("/hi")
    public String hi(@RequestParam(value = "name") String name) {
        return hiService.hi(name);
    }

}
