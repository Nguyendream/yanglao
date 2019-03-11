package com.test.yanglao.controller;

import com.github.pagehelper.PageInfo;
import com.test.yanglao.common.Const;
import com.test.yanglao.common.ResponseCode;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceId;
import com.test.yanglao.pojo.User;
import com.test.yanglao.service.DeviceFileService;
import com.test.yanglao.service.DeviceService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/device/")
public class DeviceManageController {

    @Resource
    private DeviceService deviceService;

    @Resource
    private DeviceFileService deviceFileService;

    @RequestMapping(value = "list_files.do")
    public ServerResponse listFiles(HttpSession session, Integer deviceId,
                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        if (!deviceService.getDeviceByUserAndDeviceId(user.getId(), deviceId).isSuccess()) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.DEVICE_NULL.getCode(),"设备号未注册");
        }

        return deviceFileService.getFileListById(deviceId, pageNum, pageSize);
    }

    @RequestMapping(value = "list_logs.do")
    public ServerResponse<PageInfo> listLogs(HttpSession session, Integer deviceId,
                                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        if (!deviceService.getDeviceByUserAndDeviceId(user.getId(), deviceId).isSuccess()) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.DEVICE_NULL.getCode(),"设备号未注册");
        }

        return deviceService.selectLogsById(deviceId, pageNum, pageSize);
    }

    @RequestMapping(value = "download/{fileId}")
    public ResponseEntity<InputStreamResource> fileDownload(HttpSession session, @PathVariable int fileId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return deviceFileService.fileDownload(fileId, user.getId());
    }
}
