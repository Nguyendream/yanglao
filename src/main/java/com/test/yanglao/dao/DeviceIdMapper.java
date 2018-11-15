package com.test.yanglao.dao;

import com.test.yanglao.pojo.DeviceId;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceIdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceId record);

    int insertSelective(DeviceId record);

    DeviceId selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceId record);

    int updateByPrimaryKey(DeviceId record);
}