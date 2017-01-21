package com.air.service.impl;

import com.air.mapper.AirUserMapper;
import com.air.pojo.AirUser;
import com.air.service.AirUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */
@Service
public class AirUserServiceImpl implements AirUserService {
    @Resource
    private AirUserMapper airUserMapper;
    @Override
    public List queryAirUser() {
        return airUserMapper.selectAllAirUser();
    }

    @Override
    public boolean addAirUser(AirUser airUser) {
        if(airUserMapper.selectByOpenId(airUser.getOpenid())==null) {
            return airUserMapper.insert(airUser) == 1 ? true : false;
        }else {
            return true;
        }
    }

    @Override
    public boolean updateAirUser(AirUser airUser) {
        return airUserMapper.updateByPrimaryKey(airUser)==1?true:false;
    }

    @Override
    public boolean delAirUser(AirUser airUser) {
        return airUserMapper.deleteByPrimaryKey(airUser.getUser_id())==1?true:false;
    }
}
