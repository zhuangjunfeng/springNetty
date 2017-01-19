package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("F1")
public class HeartbeatActor {
    private static Logger logger = Logger.getLogger(HeartbeatActor.class);

    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;
    private Map webSocketClient;


    public HeartbeatActor(){}

    public HeartbeatActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.heartbeat();
        this.sendWeb();
    }

    public void heartbeat(){
        modbus.setCODE("80");
        modbus.setDATA("F101");
        channel.writeAndFlush(modbus);
    }

    public void sendWeb(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        Channel incoming = (Channel)webSocketClient.get(modbus.getUID());
        if(incoming!=null) {
            incoming.writeAndFlush(new TextWebSocketFrame(new Date().toString()+"-收到终端" + modbus.getUID() + "的心跳数据："+modbus.getDATA()));
        }
    }

    public Map getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(Map webSocketClient) {
        this.webSocketClient = webSocketClient;
    }
}
