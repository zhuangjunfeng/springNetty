package com.air.netty.websocket.actor;

import com.air.netty.client.protocol.Modbus;
import com.air.netty.websocket.protocol.WebSocketMsg;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
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
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map maps = objectMapper.readValue(webSocketMsg.getData(), Map.class);
            String uid =maps.get("senduid").toString();
            if(uid.equals("")){
                channel.writeAndFlush(new TextWebSocketFrame("设备 UID 不能为空！！！"));
            }else {
                clientSocket=(Map) servletContext.getAttribute("clientMap");

                Channel incoming = (Channel)clientSocket.get(uid);
                if(incoming!=null) {

                    modbus.setUID(uid);
                    modbus.setCODE(maps.get("code").toString());
                    modbus.setDATA(maps.get("data").toString());
                    incoming.writeAndFlush(modbus);
                    channel.writeAndFlush(new TextWebSocketFrame("发送命令"+modbus.getCODE()+"和数据"+modbus.getDATA()+"到设备：" + uid));
                }else {
                    channel.writeAndFlush(new TextWebSocketFrame("发送失败：设备" + uid+"没有在线！！！"));
                }

            }


        }catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
