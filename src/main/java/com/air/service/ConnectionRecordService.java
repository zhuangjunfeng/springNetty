package com.air.service;

import com.air.pojo.ConnectionRecord;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/11.
 */
@Service
public interface ConnectionRecordService {
    void saveRecord(ConnectionRecord connectionRecord);
}
