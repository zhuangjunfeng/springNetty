package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import com.air.netty.websocket.protocol.WebSocketMsg;
import com.air.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("01")
public class QueryRtuActor {
    protected static final Logger logger = LoggerFactory.getLogger(QueryRtuActor.class);

    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;
    private Map<String,Channel> webSocketClient;
    private Map<String,Channel> clientSocket;
    private Map<String,String> wsUIDMap;

    public QueryRtuActor(){}

    public QueryRtuActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.sendWeb();
        if(modbus.getDATA().substring(0,2).equals("28")){

        }
        switch (modbus.getDATA().substring(0,2)){
            case "28" :
                this.querySensor();
                break;
            case "29" :
                this.queryStatus();
                break;
        }
    }

    public void querySensor(){

    }
    public void queryStatus(){

    }


    public void sendWeb(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        wsUIDMap=(Map) servletContext.getAttribute("wsUIDMap");
        String UID = modbus.getUID();
        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setUid(UID);
        webSocketMsg.setCmd(modbus.getCODE());
        webSocketMsg.setData(modbus.getDATA());
        String rs= StringUtils.ObjectToJson(webSocketMsg);
        logger.info("收到设备指令："+rs);
        //遍历wsUIDMap获取所有监听UID的ws通道
        for(Map.Entry<String,String> entry:wsUIDMap.entrySet()){
            if(entry.getValue().equals(UID)){
                Channel incoming = (Channel)webSocketClient.get(entry.getKey());
                if(incoming!=null) {
                    incoming.writeAndFlush(new TextWebSocketFrame(rs));
                }
            }
        }
    }
}
