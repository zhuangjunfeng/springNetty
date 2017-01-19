package com.air.service.impl;

import com.air.mapper.AirDeviceMapper;
import com.air.pojo.AirDevice;
import com.air.service.AirDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */
@Service
public class AirDeviceServiceImpl implements AirDeviceService{
    @Resource
    private AirDeviceMapper airDeviceMapper;
    @Override
    public List queryDevice() {
        return null;
    }

    @Override
    public boolean addDevice(AirDevice airDevice) {
        return false;
    }

    @Override
    public boolean updateDevice(AirDevice airDevice) {
        return false;
    }

    @Override
    public boolean delDevice(AirDevice airDevice) {
        return false;
    }
}
