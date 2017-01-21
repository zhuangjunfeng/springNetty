package com.air.service;

import com.air.pojo.AirWxInfo;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2017/1/21.
 */
@Service
public interface AirWxInfoService {
    public AirWxInfo queryWxInfo();
}
