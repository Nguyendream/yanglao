package com.test.yanglao.dao;

import com.test.yanglao.pojo.DeviceLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    List<DeviceLogs> selectListByDeviceIdAndDate(@Param("deviceId") Integer deviceId,
                                                 @Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate);

    Date selectLastDateByDeviceId(Integer deviceId);

    int deleteByDeviceId(Integer deviceId);
}