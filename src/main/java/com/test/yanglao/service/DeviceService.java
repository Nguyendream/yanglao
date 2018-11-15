package com.test.yanglao.service;

import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceLogs;

import java.util.List;

public interface DeviceService {

    ServerResponse<String> addLogs(DeviceLogs deviceLogs);

    ServerResponse<List<DeviceLogs>> selectLogsById(Integer deviceId);
}
