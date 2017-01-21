package com.air.service;

import com.air.pojo.AirUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */
@Service
public interface AirUserService {
    public List queryAirUser();
    public boolean addAirUser(AirUser airUser);
    public boolean updateAirUser(AirUser airUser);
    public boolean delAirUser(AirUser airUser);
}
