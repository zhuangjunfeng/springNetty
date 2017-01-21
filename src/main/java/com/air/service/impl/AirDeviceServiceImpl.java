package com.air.service.impl;

import com.air.mapper.AirDeviceMapper;
import com.air.mapper.AirUserDeviceMapper;
import com.air.pojo.AirDevice;
import com.air.pojo.AirUser;
import com.air.pojo.AirUserDevice;
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
    @Resource
    private AirUserDeviceMapper airUserDeviceMapper;
    @Override
    public List queryDevice() {
        return airDeviceMapper.selectAllAirDevice();
    }

    @Override
    public boolean addDevice(AirDevice airDevice,AirUser airUser) {

        AirUserDevice airUserDevice = new AirUserDevice();
        int device_id =airDeviceMapper.insert(airDevice);
        airUserDevice.setDevice_id(device_id);
        airUserDevice.setOpenid(airUser.getOpenid());
        airUserDeviceMapper.insert(airUserDevice);

        return airUserDeviceMapper.insert(airUserDevice)==1?true:false;
    }

    @Override
    public boolean updateDevice(AirDevice airDevice) {
        return airDeviceMapper.updateByPrimaryKeySelective(airDevice)==1?true:false;
    }

    @Override
    public boolean delDevice(AirDevice airDevice) {
        return airDeviceMapper.deleteByPrimaryKey(airDevice.getDevice_id())==1?true:false;
    }
}
