package com.air.service;

import com.air.pojo.AirDevice;
import com.air.pojo.AirUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */
@Service
public interface AirDeviceService {
    public List queryDevice(String openid);
    public List queryDeviceOpenid(String uid);

    public boolean addDevice(AirDevice airDevice,AirUser airUser);
    public boolean updateDevice(AirDevice airDevice);
    public boolean delDevice(AirDevice airDevice);
}
