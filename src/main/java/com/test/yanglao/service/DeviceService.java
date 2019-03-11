package com.test.yanglao.service;

import com.github.pagehelper.PageInfo;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceId;
import com.test.yanglao.pojo.DeviceLogs;

import java.util.List;

public interface DeviceService {

    ServerResponse<String> addDevice(DeviceId deviceId);

    ServerResponse<String> updateDevice(DeviceId deviceId);

    ServerResponse<DeviceId> getDeviceByUserAndDeviceId(Integer userId, Integer deviceId);

    ServerResponse<List<DeviceId>> selectDeviceByUserId(Integer userId);

    ServerResponse<String> deleteDevice(Integer userId, Integer deviceId);

    ServerResponse<String> checkIdValid(Integer id, String type);

    //DeviceLogs
    ServerResponse<String> addLogs(DeviceLogs deviceLogs);

    ServerResponse<PageInfo> selectLogsById(Integer deviceId, int pageNum, int pageSize);

    ServerResponse<String> deleteLogByUserAndDeviceId(Integer userId, Integer deviceId);
}
