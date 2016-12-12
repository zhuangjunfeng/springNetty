package com.air.mapper;

import com.air.pojo.AirUser;

public interface AirUserMapper {
    int deleteByPrimaryKey(Integer user_id);

    int insert(AirUser record);

    int insertSelective(AirUser record);

    AirUser selectByPrimaryKey(Integer user_id);

    int updateByPrimaryKeySelective(AirUser record);

    int updateByPrimaryKey(AirUser record);
}