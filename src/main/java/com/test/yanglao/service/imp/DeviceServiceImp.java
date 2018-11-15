package com.test.yanglao.service.imp;

import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.dao.DeviceLogsMapper;
import com.test.yanglao.pojo.DeviceLogs;
import com.test.yanglao.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("Device")
public class DeviceServiceImp implements DeviceService {

    @Resource
    private DeviceLogsMapper deviceLogsMapper;

    @Override
    public ServerResponse addLogs(DeviceLogs deviceLogs) {

        int resultCount = deviceLogsMapper.insert(deviceLogs);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("Error");
        } else {
            return ServerResponse.createBySuccessMessage("Success");
        }
    }

    @Override
    public ServerResponse<List<DeviceLogs>> selectLogsById(Integer deviceId) {

        List<DeviceLogs> list = deviceLogsMapper.selectListByDeviceId(deviceId);
        if (list == null) {
            return ServerResponse.createByErrorMessage("Error");
        }
        return ServerResponse.createBySuccess(list);
    }
}
