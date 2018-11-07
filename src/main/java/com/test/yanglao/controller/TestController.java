package com.test.yanglao.controller;


import com.test.yanglao.common.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ServerResponse<String> testJson() {


        return null;
    }
}
