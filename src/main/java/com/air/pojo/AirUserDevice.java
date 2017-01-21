package com.air.pojo;

public class AirUserDevice {
    private Integer user_device_id;

    private String openid;

    private Integer device_id;

    public Integer getUser_device_id() {
        return user_device_id;
    }

    public void setUser_device_id(Integer user_device_id) {
        this.user_device_id = user_device_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }
}