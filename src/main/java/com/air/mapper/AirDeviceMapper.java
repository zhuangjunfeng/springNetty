package com.air.mapper;

import com.air.pojo.AirDevice;
import com.air.pojo.AirUser;

import java.util.List;

public interface AirDeviceMapper {
    int deleteByPrimaryKey(Integer device_id);

    int insert(AirDevice record);

    int insertSelective(AirDevice record);

    AirDevice selectByUid(String device_uid,String openid);

    int updateByPrimaryKeySelective(AirDevice record);

    int updateByPrimaryKey(AirDevice record);

    List<AirDevice> selectAllAirDevice(String openid);


}