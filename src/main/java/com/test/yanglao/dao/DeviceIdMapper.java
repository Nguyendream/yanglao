package com.test.yanglao.dao;

import com.test.yanglao.pojo.DeviceId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeviceIdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceId record);

    int insertSelective(DeviceId record);

    DeviceId selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceId record);

    int updateByPrimaryKey(DeviceId record);

    int checkDeviceId(Integer deviceId);

    DeviceId selectByDeviceIdUserId(@Param("deviceId") Integer deviceId, @Param("userId") Integer userId);

    List<DeviceId> selectByUserId(Integer userId);

    int updateByDeviceIdSelective(DeviceId deviceId);

    int deleteByDeviceIdUserId(@Param("deviceId") Integer deviceId, @Param("userId") Integer userId);
}