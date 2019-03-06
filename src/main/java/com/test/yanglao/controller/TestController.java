package com.test.yanglao.controller;


import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceLogs;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    @RequestMapping(value = "/upload_test.do", method = RequestMethod.POST)
    public ServerResponse<String> uploadTest(Integer deviceId, MultipartFile file) {

        return ServerResponse.createBySuccessMessage("Success, deviceId: " + deviceId
                                                    + "; fileName: " + file.getOriginalFilename()
                                                    + "; fileContentType: " + file.getContentType()
                                                    + "; size: " + file.getSize());
    }
}
