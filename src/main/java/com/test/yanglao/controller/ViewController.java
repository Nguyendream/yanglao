package com.test.yanglao.controller;

import com.test.yanglao.common.Const;
import com.test.yanglao.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class ViewController {

    //@RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public String index(HttpSession session) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return "index";
        }
        return "/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

}
