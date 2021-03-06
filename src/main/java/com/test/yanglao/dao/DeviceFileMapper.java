package com.test.yanglao.dao;

import com.test.yanglao.pojo.DeviceFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceFile record);

    int insertSelective(DeviceFile record);

    DeviceFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceFile record);

    int updateByPrimaryKey(DeviceFile record);

    List<DeviceFile> selectByDeviceId(Integer deviceId);
}