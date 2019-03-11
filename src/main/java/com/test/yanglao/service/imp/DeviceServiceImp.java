package com.test.yanglao.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.yanglao.common.Const;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.dao.DeviceIdMapper;
import com.test.yanglao.dao.DeviceLogsMapper;
import com.test.yanglao.pojo.DeviceId;
import com.test.yanglao.pojo.DeviceLogs;
import com.test.yanglao.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("Device")
public class DeviceServiceImp implements DeviceService {

    @Resource
    private DeviceLogsMapper deviceLogsMapper;

    @Resource
    private DeviceIdMapper deviceIdMapper;

    @Override
    public ServerResponse<String> addDevice(DeviceId deviceId) {

        if (deviceId.getUserId() == null || deviceId.getDeviceId() == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        if (this.checkIdValid(deviceId.getDeviceId(), Const.DEVICE_ID).isSuccess()) {
            int resultCount = deviceIdMapper.insert(deviceId);
            if (resultCount == 0) {
                return ServerResponse.createByErrorMessage("设备添加失败");
            }
            return ServerResponse.createBySuccessMessage("设备添加成功");
        } else {
            return ServerResponse.createByErrorMessage("该设备已被使用");
        }
    }

    @Override
    public ServerResponse<String> updateDevice(DeviceId deviceId) {
        if (deviceId.getDeviceId() == null || deviceId.getUserId() == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        if (!this.getDeviceByUserAndDeviceId(deviceId.getUserId(), deviceId.getDeviceId()).isSuccess()) {
            return ServerResponse.createByErrorMessage("设备参数更新错误，找不到该设备");
        }
        int resultCount = deviceIdMapper.updateByPrimaryKeySelective(deviceId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("参设备数更新成功");
        }
        return ServerResponse.createByErrorMessage("设备参数更新失败");
    }

    @Override
    public ServerResponse<DeviceId> getDeviceByUserAndDeviceId(Integer userId, Integer deviceId) {

        DeviceId deviceId1 = deviceIdMapper.selectByDeviceIdUserId(deviceId, userId);
        if (deviceId1 == null) {
            return ServerResponse.createByErrorMessage("查询无结果");
        }

        return ServerResponse.createBySuccess(deviceId1);
    }

    @Override
    public ServerResponse<List<DeviceId>> selectDeviceByUserId(Integer userId) {

        List<DeviceId> deviceIds = deviceIdMapper.selectByUserId(userId);
        return ServerResponse.createBySuccess(deviceIds);
    }

    @Override
    public ServerResponse<String> deleteDevice(Integer userId, Integer deviceId) {

        int resultCount = deviceIdMapper.deleteByDeviceIdUserId(deviceId, userId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除设备成功");
        }
        return null;
    }

    @Override
    public ServerResponse<String> checkIdValid(Integer id, String type) {

        if (StringUtils.isNotBlank(type)) {
            //校验
            if (Const.DEVICE_ID.equals(type)) {
                int resultCount = deviceIdMapper.checkDeviceId(id);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("设备已存在");
                }
            }

        }
        return null;
    }

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
    public ServerResponse<PageInfo> selectLogsById(Integer deviceId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<DeviceLogs> list = deviceLogsMapper.selectListByDeviceId(deviceId);

        PageInfo pageInfo = new PageInfo(list);

        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<String> deleteLogByUserAndDeviceId(Integer userId, Integer deviceId) {
        return null;
    }
}
