package com.test.yanglao.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.yanglao.common.Const;
import com.test.yanglao.common.ResponseCode;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.dao.DeviceIdMapper;
import com.test.yanglao.dao.DeviceLogsMapper;
import com.test.yanglao.pojo.DeviceId;
import com.test.yanglao.pojo.DeviceLogs;
import com.test.yanglao.service.DeviceService;
import com.test.yanglao.vo.DeviceIdListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.DEVICE_REGISTERED.getCode(), "设备号已被注册");
        }
    }

    @Override
    public ServerResponse<String> updateDevice(DeviceId deviceId) {
        if (deviceId.getDeviceId() == null || deviceId.getUserId() == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        if (!this.getDeviceByUserAndDeviceId(deviceId.getUserId(), deviceId.getDeviceId()).isSuccess()) {
            return ServerResponse.createByErrorMessage("设备信息更新错误，找不到该设备");
        }
        int resultCount = deviceIdMapper.updateByDeviceIdSelective(deviceId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("设备信息更新成功");
        }
        return ServerResponse.createByErrorMessage("设备信息更新失败");
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
    public ServerResponse<PageInfo> selectDeviceByUserId(Integer userId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<DeviceId> deviceIds = deviceIdMapper.selectByUserId(userId);

        List<DeviceIdListVo> deviceIdListVoList = new ArrayList<>();
        for (DeviceId item : deviceIds) {
            DeviceIdListVo deviceIdListVo = assembleDeviceIdListVo(item);
            deviceIdListVoList.add(deviceIdListVo);
        }

        PageInfo pageInfo = new PageInfo(deviceIds);
        pageInfo.setList(deviceIdListVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<String> deleteDevice(Integer userId, Integer deviceId) {

        //删除设备相关历史记录
        String logCount = "";
        try {
            ServerResponse<String> response = this.deleteLogByUserAndDeviceId(userId, deviceId);
            logCount = response.getData();
            if (!response.isSuccess()) {
                return response;
            }
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("设备记录删除失败");
        }

        //删除设备
        int resultCount = deviceIdMapper.deleteByDeviceIdUserId(deviceId, userId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除设备成功, 共删除" + logCount + "条记录");
        }
        return ServerResponse.createByErrorMessage("设备删除失败");
    }

    @Override
    public ServerResponse<String> checkIdValid(Integer id, String type) {

        if (StringUtils.isNotBlank(type)) {
            //校验
            if (Const.DEVICE_ID.equals(type)) {
                int resultCount = deviceIdMapper.checkDeviceId(id);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorCodeMessage(
                            ResponseCode.DEVICE_REGISTERED.getCode(), "设备已存在");
                }
            }

        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse addLogs(DeviceLogs deviceLogs) {

        //检验设备是否存在
        if (this.checkIdValid(deviceLogs.getDeviceId(), Const.DEVICE_ID).isSuccess()) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.DEVICE_NULL.getCode(),
                    ResponseCode.DEVICE_NULL.getDesc()
            );
        }

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

        DeviceId deviceId1 = deviceIdMapper.selectByDeviceIdUserId(deviceId, userId);
        if (deviceId1 == null) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.DEVICE_NULL.getCode(), "没有该设备");
        }

        int resultCount = deviceLogsMapper.deleteByDeviceId(deviceId);
        return ServerResponse.createBySuccess("共删除" + resultCount + "条记录", resultCount+"");
    }


    private DeviceIdListVo assembleDeviceIdListVo(DeviceId deviceId) {
        DeviceIdListVo deviceIdListVo = new DeviceIdListVo();
        deviceIdListVo.setId(deviceId.getId());
        deviceIdListVo.setDeviceId(deviceId.getDeviceId());
        deviceIdListVo.setDeviceName(deviceId.getDeviceName());
        deviceIdListVo.setCreateTime(deviceId.getCreateTime());
        deviceIdListVo.setUpdateTime(deviceId.getUpdateTime());

        return deviceIdListVo;
    }
}
