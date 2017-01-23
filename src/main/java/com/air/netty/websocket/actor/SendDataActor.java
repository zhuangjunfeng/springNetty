package com.air.netty.websocket.actor;

import com.air.netty.client.protocol.Modbus;
import com.air.netty.websocket.protocol.WebSocketMsg;
import com.air.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Map;

/**
 * @Description
 * @Author semstouch
 * @Date 2017/1/18
 **/
@Controller
public class SendDataActor {
    private static Logger logger = Logger.getLogger(SendDataActor.class);

    private WebSocketMsg webSocketMsg;
    private Channel channel;
    private ServletContext servletContext;
    private Map clientSocket;
    private Modbus modbus;



    public SendDataActor(){}

    public SendDataActor(WebSocketMsg webSocketMsg,Channel incoming,ServletContext servletContext){
        this.webSocketMsg=webSocketMsg;
        this.channel=incoming;
        this.servletContext=servletContext;
        modbus = new Modbus();
        this.sendMsg();
    }

    public void sendMsg(){
            String uid =webSocketMsg.getUid();
            if(uid.equals("")){
                webSocketMsg.setData("设备 UID 不能为空！");
                channel.writeAndFlush(new TextWebSocketFrame(StringUtils.ObjectToJson(webSocketMsg)));
            }else {
                clientSocket=(Map) servletContext.getAttribute("clientMap");
                Channel incoming = (Channel)clientSocket.get(uid);
                if(incoming!=null) {
                    modbus.setUID(uid);
                    modbus.setCODE(webSocketMsg.getCode());
                    modbus.setDATA(webSocketMsg.getData());
                    incoming.writeAndFlush(modbus);
                    channel.writeAndFlush(new TextWebSocketFrame("发送命令"+modbus.getCODE()+"和数据"+modbus.getDATA()+"到设备：" + uid));
                }else {
                    webSocketMsg.setData("设备不在线！");
                    String rs= StringUtils.ObjectToJson(webSocketMsg);
                    logger.info("发送ws信息："+rs);
                    channel.writeAndFlush(new TextWebSocketFrame(rs));
                }
            }
    }

    public Map getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Map clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Modbus getModbus() {
        return modbus;
    }

    public void setModbus(Modbus modbus) {
        this.modbus = modbus;
    }
}
