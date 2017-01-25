package com.air.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author semstouch
 * @Date 2017/1/25
 **/
public class StautsMsgTemplateEntity {
    private Map<String, String> first;
    private Map<String, String> keynote1;
    private Map<String, String> keynote2;
    private Map<String, String> keynote3;
    private Map<String,String> keynote4;
    private Map<String,String> keynote5;
    private Map<String,String> remark;

    public Map<String, String> getFirst() {
        return first;
    }

    public void setFirst(Map<String, String> first) {
        this.first = first;
    }
    public void setFirstData(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.first = map;
    }

    public Map<String, String> getKeynote1() {
        return keynote1;
    }

    public void setKeynote1(Map<String, String> keynote1) {
        this.keynote1 = keynote1;
    }
    public void setKeynote1Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keynote1 = map;
    }


    public Map<String, String> getKeynote2() {
        return keynote2;
    }

    public void setKeynote2(Map<String, String> keynote2) {
        this.keynote2 = keynote2;
    }
    public void setKeynote2Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keynote2 = map;
    }

    public Map<String, String> getKeynote3() {
        return keynote3;
    }

    public void setKeynote3(Map<String, String> keynote3) {
        this.keynote3 = keynote3;
    }
    public void setKeynote3Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keynote3 = map;
    }


    public Map<String, String> getKeynote4() {
        return keynote4;
    }

    public void setKeynote4(Map<String, String> keynote4) {
        this.keynote4 = keynote4;
    }
    public void setKeynote4Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keynote4 = map;
    }

    public Map<String, String> getKeynote5() {
        return keynote5;
    }

    public void setKeynote5(Map<String, String> keynote5) {
        this.keynote5 = keynote5;
    }
    public void setKeynote5Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keynote5 = map;
    }

    public Map<String, String> getRemark() {
        return remark;
    }

    public void setRemark(Map<String, String> remark) {
        this.remark = remark;
    }
    public void setRemarkData(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.remark = map;
    }
}

