package com.air.mapper;

import com.air.pojo.AirWxInfo;

import java.util.List;

public interface AirWxInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirWxInfo record);

    int insertSelective(AirWxInfo record);

    AirWxInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AirWxInfo record);

    int updateByPrimaryKey(AirWxInfo record);

    List<AirWxInfo> selectAllAirWxInfo();
}