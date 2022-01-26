package io.github.hackyoma.springclouddemo.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.hackyoma.springclouddemo.common.RequestUtil;
import io.github.hackyoma.springclouddemo.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * @author hackyo
 * @version 2018/8/22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/_login")
    public String login(@RequestBody JSONObject loginInfo) {
        return userService.login(loginInfo);
    }

    @GetMapping("/all_info")
    public JSONObject getCurrentUserInfo(@RequestHeader(RequestUtil.USER_ID) String currentUserId) {
        return userService.getUserInfo(currentUserId);
    }

}
