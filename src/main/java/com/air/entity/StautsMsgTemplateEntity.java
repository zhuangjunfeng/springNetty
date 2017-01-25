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
    private Map<String, String> keyword1;
    private Map<String, String> keyword2;
    private Map<String, String> keyword3;
    private Map<String,String> keyword4;
    private Map<String,String> keyword5;
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

    public Map<String, String> getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(Map<String, String> keyword1) {
        this.keyword1 = keyword1;
    }
    public void setKeyword1Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keyword1 = map;
    }

    public Map<String, String> getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(Map<String, String> keyword2) {
        this.keyword2 = keyword2;
    }
    public void setKeyword2Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keyword2 = map;
    }

    public Map<String, String> getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(Map<String, String> keyword3) {
        this.keyword3 = keyword3;
    }
    public void setKeyword3Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keyword3 = map;
    }
    public Map<String, String> getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(Map<String, String> keyword4) {
        this.keyword4 = keyword4;
    }
    public void setKeyword4Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keyword4 = map;
    }

    public Map<String, String> getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(Map<String, String> keyword5) {
        this.keyword5 = keyword5;
    }
    public void setKeyword5Data(String value,String color) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("value",value);
        map.put("color",color);
        this.keyword5 = map;
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

