package com.test.yanglao.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String testGet() {
        return "testGet, success!!!";
    }
}
