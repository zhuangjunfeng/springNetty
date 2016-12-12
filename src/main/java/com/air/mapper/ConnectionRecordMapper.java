package com.air.mapper;

import com.air.pojo.ConnectionRecord;

public interface ConnectionRecordMapper {
    int deleteByPrimaryKey(Integer record_id);

    int insert(ConnectionRecord record);

    int insertSelective(ConnectionRecord record);

    ConnectionRecord selectByPrimaryKey(Integer record_id);

    int updateByPrimaryKeySelective(ConnectionRecord record);

    int updateByPrimaryKey(ConnectionRecord record);
}