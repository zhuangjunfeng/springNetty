package com.air.mapper;

import com.air.pojo.AirUserDevice;

public interface AirUserDeviceMapper {
    int deleteByPrimaryKey(Integer user_device_id);

    int insert(AirUserDevice record);

    int insertSelective(AirUserDevice record);

    AirUserDevice selectByPrimaryKey(Integer user_device_id);

    int updateByPrimaryKeySelective(AirUserDevice record);

    int updateByPrimaryKey(AirUserDevice record);
}