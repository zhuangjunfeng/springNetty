package com.air.service;

import com.air.pojo.AirDevice;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */
@Service
public interface AirDeviceService {
    public List queryDevice();
    public boolean addDevice(AirDevice airDevice);
    public boolean updateDevice(AirDevice airDevice);
    public boolean delDevice(AirDevice airDevice);
}
