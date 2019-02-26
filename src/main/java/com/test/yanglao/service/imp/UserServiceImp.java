package com.test.yanglao.service.imp;

import com.test.yanglao.common.Const;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.dao.DeviceIdMapper;
import com.test.yanglao.dao.UserMapper;
import com.test.yanglao.pojo.DeviceId;
import com.test.yanglao.pojo.User;
import com.test.yanglao.service.UserService;
import com.test.yanglao.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImp implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DeviceIdMapper deviceIdMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {

        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //密码MD5加密
        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse<String> register(User user, String deviceNum) {

        //验证是否存在用户名,手机号,设备号
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getPhone(), Const.PHONE);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(deviceNum, Const.DEVICE_ID);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_USER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        DeviceId deviceId = new DeviceId();
        deviceId.setDeviceId(Integer.parseInt(deviceNum));

        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        // todo insert deviceId table
        //resultCount = deviceIdMapper.insert();
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {

        if (StringUtils.isNotBlank(type)) {
            //校验
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.PHONE.equals(type)) {
                int resultCount = userMapper.checkPhone(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("手机号已存在");
                }
            }
            // 设备号校验
            /*if (Const.DEVICE_ID.equals(type)) {
                int resultCount = deviceIdMapper.checkDeviceId(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("该设备已注册");
                }
            }*/
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<List<User>> list() {

        List<User> userList = userMapper.selectAllUsers();
        User user;
        for (int i = 0; i < userList.size(); i++) {
            user = userList.get(i);
            user.setPassword(StringUtils.EMPTY);
            userList.set(i, user);
        }
        return ServerResponse.createBySuccess(userList);
    }

}
