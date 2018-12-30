package com.test.yanglao.service;

import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.User;

import java.util.List;

public interface UserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user, String deviceNum);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<List<User>> list();
}
