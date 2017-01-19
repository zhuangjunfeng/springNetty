package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description  终端登录
 * @Author semstouch
 * @Date 2016/12/13
 **/
@Component("F0")
public class LoginActor {
    private static Logger logger = Logger.getLogger(LoginActor.class);
    private  Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;
    private Map webSocketClient;
    private Map clientSocket;
    private Map ipUIDMap;

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
        String uid =modbus.getUID();
        channel.writeAndFlush(modbus);


        //UID-channel关系缓存

        clientSocket=(Map) servletContext.getAttribute("clientMap");
        if(clientSocket==null) {
            clientSocket=new HashMap();
            clientSocket.put(uid, channel);

        }else {
            clientSocket.put(uid, channel);
        }
        servletContext.setAttribute("clientMap", clientSocket);


        //UID-IP关系缓存

        ipUIDMap= (Map) servletContext.getAttribute("ipUIDMap");
        if(ipUIDMap==null){
            ipUIDMap=new HashMap();
            ipUIDMap.put(channel.remoteAddress().toString(),uid);
        }else {
            ipUIDMap.put(channel.remoteAddress().toString(),uid);
        }

        servletContext.setAttribute("ipUIDMap",ipUIDMap);
        logger.info(modbus.getUID()+"连接开启····");



    }

    public void sendWeb(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        Channel incoming = (Channel)webSocketClient.get(modbus.getUID());
        if(incoming!=null) {
            incoming.writeAndFlush(new TextWebSocketFrame(new Date().toString()+"-终端" + modbus.getUID() + "上线"));
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

    public Map getIpUIDMap() {
        return ipUIDMap;
    }

    public void setIpUIDMap(Map ipUIDMap) {
        this.ipUIDMap = ipUIDMap;
    }
}
