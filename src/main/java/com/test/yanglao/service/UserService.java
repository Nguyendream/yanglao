package com.test.yanglao.service;

import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.User;

public interface UserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

}
