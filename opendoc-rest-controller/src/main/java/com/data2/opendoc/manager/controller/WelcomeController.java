package com.data2.opendoc.manager.controller;

import com.data2.opendoc.manager.server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController
public class WelcomeController {
    @GetMapping("test")
    public String test() {
        return "welcome to opendoc";
    }

    @Autowired
    private UserMapper userMapper;
    @GetMapping("testdb")
    public Object testdb() {
        return userMapper.selectById(1);
    }
}
