package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("F1")
public class HeartbeatActor {
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(HeartbeatActor.class);

    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;
    private Map<String,Channel> webSocketClient;
    private Map<String,Channel> clientSocket;
    private Map<String,String> wsUIDMap;


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
        wsUIDMap=(Map) servletContext.getAttribute("wsUIDMap");
        String UID = modbus.getUID();

        //遍历wsUIDMap获取所有监听UID的ws通道
        for(Map.Entry<String,String> entry:wsUIDMap.entrySet()){
            if(entry.getValue().equals(UID)){
                Channel incoming = (Channel)webSocketClient.get(entry.getKey());
                if(incoming!=null) {
                    incoming.writeAndFlush(new TextWebSocketFrame(new Date().toString()+"-收到终端" + UID + "的心跳数据："+modbus.getDATA()));
                }
            }
        }
    }

    public Map<String, String> getWsUIDMap() {
        return wsUIDMap;
    }

    public void setWsUIDMap(Map<String, String> wsUIDMap) {
        this.wsUIDMap = wsUIDMap;
    }

    public Map<String, Channel> getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Map<String, Channel> clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Map<String, Channel> getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(Map<String, Channel> webSocketClient) {
        this.webSocketClient = webSocketClient;
    }
}
