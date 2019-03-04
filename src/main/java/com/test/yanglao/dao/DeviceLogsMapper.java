package com.test.yanglao.dao;

import com.test.yanglao.pojo.DeviceLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceLogsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceLogs record);

    int insertSelective(DeviceLogs record);

    DeviceLogs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceLogs record);

    int updateByPrimaryKey(DeviceLogs record);

    List<DeviceLogs> selectListByDeviceId(Integer deviceId);

    int deleteByDeviceId(Integer deviceId);
}