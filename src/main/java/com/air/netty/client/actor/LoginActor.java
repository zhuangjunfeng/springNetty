package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * @Description  终端登录
 * @Author semstouch
 * @Date 2016/12/13
 **/
@Component("F0")
public class LoginActor {
    private  Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;
    private Map webSocketClient;
    private Map clientSocket;

    public LoginActor(){}

    public LoginActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.login();
        this.sendWeb();
    }

    public void login(){
        modbus.setCODE("80");
        modbus.setDATA("F001");
        channel.writeAndFlush(modbus);
        clientSocket=(Map) servletContext.getAttribute("clientMap");
        clientSocket.put(modbus.getUID(),channel);

    }

    public void sendWeb(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        Channel incoming = (Channel)webSocketClient.get(modbus.getUID());
        if(incoming!=null) {
            incoming.writeAndFlush(new TextWebSocketFrame("终端" + modbus.getUID() + "上线"));
        }
    }

    public Map getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(Map webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public Map getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Map clientSocket) {
        this.clientSocket = clientSocket;
    }
}
