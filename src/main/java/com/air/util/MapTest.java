package com.air.util;

import com.air.pojo.AirUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @Author semstouch
 * @Date 2017/1/20
 **/
public class MapTest {
    public static void main(String[] args) {
        try {
            String json = "{\"aaa\":\"address\",\"nickname\":\"haha\",\"sex\":1,\"province\":\"email\"}";
            ObjectMapper mapper = new ObjectMapper();
            AirUser airUser = new AirUser();
             airUser = mapper.readValue(json, AirUser.class);
            airUser.getSex();
        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
