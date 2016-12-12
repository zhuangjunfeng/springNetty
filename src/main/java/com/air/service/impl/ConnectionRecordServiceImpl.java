package com.air.service.impl;

import com.air.mapper.ConnectionRecordMapper;
import com.air.pojo.ConnectionRecord;
import com.air.service.ConnectionRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/12/11.
 */
@Service
public class ConnectionRecordServiceImpl implements ConnectionRecordService{
    @Resource
    private ConnectionRecordMapper connectionRecordMapper;
    public void saveRecord(ConnectionRecord connectionRecord){
        connectionRecordMapper.insert(connectionRecord);
    }
}