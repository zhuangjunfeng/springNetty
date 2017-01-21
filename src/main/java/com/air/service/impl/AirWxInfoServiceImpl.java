package com.air.service.impl;

import com.air.mapper.AirWxInfoMapper;
import com.air.pojo.AirWxInfo;
import com.air.service.AirWxInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/1/21.
 */
@Service
public class AirWxInfoServiceImpl  implements AirWxInfoService{
    @Resource
    private AirWxInfoMapper airWxInfoMapper;
    @Override
    public AirWxInfo queryWxInfo() {
        return airWxInfoMapper.selectAllAirWxInfo().get(0);
    }
}
