package com.air.netty.client.protocol;

/**
 * @Description 协议对象类
 * @Author semstouch
 * @Date 2016/12/9
 **/
public class Modbus {
    private String  HEADER_BEGIN;
    private String  UID;
    private String  HEADER_END;
    private String  CODE;
    private String  LEN;
    private String  DATA;
    private String  CRC;
    private String  FOOTER;
    public String getLEN() {
        return LEN;
    }

    public void setLEN(String LEN) {
        this.LEN = LEN;
    }

    public String getHEADER_BEGIN() {
        return HEADER_BEGIN;
    }

    public void setHEADER_BEGIN(String HEADER_BEGIN) {
        this.HEADER_BEGIN = HEADER_BEGIN;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getHEADER_END() {
        return HEADER_END;
    }

    public void setHEADER_END(String HEADER_END) {
        this.HEADER_END = HEADER_END;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getCRC() {
        return CRC;
    }

    public void setCRC(String CRC) {
        this.CRC = CRC;
    }

    public String getFOOTER() {
        return FOOTER;
    }

    public void setFOOTER(String FOOTER) {
        this.FOOTER = FOOTER;
    }
}
