package com.test.yanglao.controller;

import com.test.yanglao.common.Const;
import com.test.yanglao.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index(HttpSession session) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return "index";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/manage")
    public String deviceManage(HttpSession session) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return "device_manage";
        }
        return "redirect:/login";
    }

}
