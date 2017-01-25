package com.air.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Description
 * @Author semstouch
 * @Date 2017/1/25
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxRespCodeEntity {
    private Integer errcode;
    private String errmsg;
    private Integer msgid;

    public Integer getMsgid() {
        return msgid;
    }

    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }
}
