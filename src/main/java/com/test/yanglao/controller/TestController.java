package com.test.yanglao.controller;


import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceLogs;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/test_json.do", method = RequestMethod.POST)
    public ServerResponse<DeviceLogs> testJson(@RequestBody DeviceLogs testJson) {

        if (testJson != null) {
            return ServerResponse.createBySuccess(testJson);
        }
        return ServerResponse.createByErrorMessage("Error Request!!!");
    }
}
