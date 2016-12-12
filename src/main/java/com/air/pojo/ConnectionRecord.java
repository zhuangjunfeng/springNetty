package com.air.pojo;

public class ConnectionRecord {
    private Integer record_id;

    private String record_ip;

    private String record_port;

    private String record_agreement;

    private String record_creat_time;

    public Integer getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
    }

    public String getRecord_ip() {
        return record_ip;
    }

    public void setRecord_ip(String record_ip) {
        this.record_ip = record_ip == null ? null : record_ip.trim();
    }

    public String getRecord_port() {
        return record_port;
    }

    public void setRecord_port(String record_port) {
        this.record_port = record_port == null ? null : record_port.trim();
    }

    public String getRecord_agreement() {
        return record_agreement;
    }

    public void setRecord_agreement(String record_agreement) {
        this.record_agreement = record_agreement == null ? null : record_agreement.trim();
    }

    public String getRecord_creat_time() {
        return record_creat_time;
    }

    public void setRecord_creat_time(String record_creat_time) {
        this.record_creat_time = record_creat_time == null ? null : record_creat_time.trim();
    }
}