package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("80")
public class ReciveActor {
    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;
    private Map webSocketClient;

    public ReciveActor(){}

    public ReciveActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.sendWeb();

    }

    public void sendWeb(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        Channel incoming = (Channel)webSocketClient.get(modbus.getUID());
        if(incoming!=null) {
            incoming.writeAndFlush(new TextWebSocketFrame(new Date().toString()+"-收到终端" + modbus.getUID() + "的数据："+modbus.getDATA()));
        }
    }

    public Map getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(Map webSocketClient) {
        this.webSocketClient = webSocketClient;
    }
}
