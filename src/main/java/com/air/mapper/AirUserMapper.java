package com.air.mapper;

import com.air.pojo.AirUser;

import java.util.List;

public interface AirUserMapper {
    int deleteByPrimaryKey(Integer user_id);

    int insert(AirUser record);

    int insertSelective(AirUser record);

    AirUser selectByPrimaryKey(Integer user_id);

    int updateByPrimaryKeySelective(AirUser record);

    AirUser selectByOpenId(String open_id);

    int updateByPrimaryKey(AirUser record);

    List<AirUser> selectAllAirUser();
}