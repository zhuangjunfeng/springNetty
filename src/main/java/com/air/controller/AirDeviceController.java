package com.air.controller;

import com.air.pojo.AirDevice;
import com.air.service.AirDeviceService;
import com.air.util.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/19.
 */
@Controller
@RequestMapping("/device")
public class AirDeviceController {
    @Resource
    private AirDeviceService airDeviceService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public  JSONResult add(@ModelAttribute("airDevice") AirDevice airDevice){
        JSONResult result=new JSONResult();;
        if(airDeviceService.addDevice(airDevice)){
            result.setMessage("success");

        }else {
            result.setMessage("error");
        }
        return result;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult find(){
        JSONResult result=new JSONResult();
        List<AirDevice> list= airDeviceService.queryDevice();
        Map airDeviceMap = new HashMap();
        airDeviceMap.put("list",list);
        result.setData(airDeviceMap);
        return result;
    }

}
