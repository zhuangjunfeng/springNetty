package com.air.netty.websocket.protocol;

/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/15
 **/
public class WebSocketMsg {
    private String cmd;
    private String data;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
