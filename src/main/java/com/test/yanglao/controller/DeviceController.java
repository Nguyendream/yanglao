package com.test.yanglao.controller;

import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceFile;
import com.test.yanglao.pojo.DeviceLogs;
import com.test.yanglao.service.DeviceFileService;
import com.test.yanglao.service.DeviceService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/device/")
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @Resource
    private DeviceFileService deviceFileService;

    @RequestMapping(value = "add_logs.do", method = RequestMethod.POST)
    public ServerResponse<String> addLogs(@RequestBody DeviceLogs deviceLogs) {

        return deviceService.addLogs(deviceLogs);
    }

    @RequestMapping(value = "list_logs.do", method = RequestMethod.POST)
    public ServerResponse<List<DeviceLogs>> listLogs(Integer deviceId) {

        return deviceService.selectLogsById(deviceId);
    }

    @RequestMapping(value = "upload_file.do", method = RequestMethod.POST)
    public ServerResponse<String> upload(Integer deviceId, MultipartFile file) {

        DeviceFile deviceFile = new DeviceFile();
        deviceFile.setDeviceId(deviceId);
        return deviceFileService.fileUpload(deviceFile, file);
    }

}
