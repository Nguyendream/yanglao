package com.test.yanglao.controller;

import com.test.yanglao.common.Const;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.User;
import com.test.yanglao.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(value="/user/")
public class UserController {


    @Resource
    private UserService userService;


    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, String remPassword, HttpSession session) {

        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
            if (remPassword == null) {
                session.setMaxInactiveInterval(30*60);//session超时时间(秒)
            } else if (remPassword.equals(Const.ON)) {
                session.setMaxInactiveInterval(3600*24*7);
            } else {
                return ServerResponse.createByErrorMessage("错误的参数");
            }
        }
        return response;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            session.removeAttribute(Const.CURRENT_USER);
            return ServerResponse.createBySuccessMessage("注销成功");
        }
        return ServerResponse.createByErrorMessage("注销失败，未登陆");
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public ServerResponse<String> register(User user, String deviceNum) {

        return userService.register(user, deviceNum);
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    public ServerResponse<String> checkValid(String str, String type) {

        return userService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    public ServerResponse<User> getUserInfo(HttpSession session) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登陆,无法获取信息");
    }

    @RequestMapping(value = "get_all_users.do", method = RequestMethod.POST)
    public ServerResponse<List<User>> getAllUsers() {

        return userService.list();
    }
}
