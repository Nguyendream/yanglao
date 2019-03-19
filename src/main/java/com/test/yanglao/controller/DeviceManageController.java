package com.test.yanglao.controller;

import com.github.pagehelper.PageInfo;
import com.test.yanglao.common.Const;
import com.test.yanglao.common.ResponseCode;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceId;
import com.test.yanglao.pojo.User;
import com.test.yanglao.service.DeviceFileService;
import com.test.yanglao.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/device/")
public class DeviceManageController {

    @Resource
    private DeviceService deviceService;

    @Resource
    private DeviceFileService deviceFileService;

    @RequestMapping("list_device.do")
    public ServerResponse<PageInfo> listDevice(HttpSession session,
                                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        ServerResponse response = deviceService.selectDeviceByUserId(user.getId(), pageNum, pageSize);
        return response;
    }

    @RequestMapping(value = "add_device.do", method = RequestMethod.POST)
    public ServerResponse addDevice(HttpSession session, DeviceId deviceId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        deviceId.setUserId(user.getId());
        if (StringUtils.isBlank(deviceId.getDeviceName())) {
            deviceId.setDeviceName("Device_" + deviceId.getDeviceId().toString());
        }

        ServerResponse response = deviceService.addDevice(deviceId);
        return response;
    }

    @RequestMapping(value = "update_device.do", method = RequestMethod.POST)
    public ServerResponse updateDevice(HttpSession session, DeviceId deviceId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        deviceId.setUserId(user.getId());

        return deviceService.updateDevice(deviceId);
    }

    @RequestMapping("delete_device.do")
    public ServerResponse deleteDevice(HttpSession session, int deviceId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        return deviceService.deleteDevice(user.getId(), deviceId);
    }

    @RequestMapping("list_files.do")
    public ServerResponse<PageInfo> listFiles(HttpSession session, Integer deviceId,
                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        if (!deviceService.getDeviceByUserAndDeviceId(user.getId(), deviceId).isSuccess()) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.DEVICE_NULL.getCode(),"设备号未注册");
        }

        return deviceFileService.getFileListById(deviceId, pageNum, pageSize);
    }

    @RequestMapping("list_logs.do")
    public ServerResponse<PageInfo> listLogs(HttpSession session, Integer deviceId,
                                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        if (!deviceService.getDeviceByUserAndDeviceId(user.getId(), deviceId).isSuccess()) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.DEVICE_NULL.getCode(),"设备号未注册");
        }

        return deviceService.selectLogsById(deviceId, pageNum, pageSize);
    }

    @RequestMapping(value = "delete_all_logs.do", method = RequestMethod.POST)
    public ServerResponse<String> deleteAllLogs(HttpSession session, int deviceId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        return deviceService.deleteLogByUserAndDeviceId(user.getId(), deviceId);
    }

    @RequestMapping("download/{fileId}")
    public ResponseEntity<InputStreamResource> fileDownload(HttpSession session, @PathVariable int fileId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return deviceFileService.fileDownload(fileId, user.getId());
    }
}
