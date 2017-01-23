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
import com.air.netty.websocket.protocol.WebSocketMsg;
/**
 * @Description  终端登录
 * @Author semstouch
 * @Date 2016/12/13
 **/
@Component("F0")
public class LoginActor{
    private static Logger logger = Logger.getLogger(LoginActor.class);
    private  Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;
    private Map<String,Channel> webSocketClient;
    private Map<String,Channel> clientSocket;
    private Map<String,String> ipUIDMap;
    private Map<String,String> wsUIDMap;

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
        String ip = channel.remoteAddress().toString();
        if(ipUIDMap==null){
            ipUIDMap=new HashMap();
            ipUIDMap.put(ip,uid);
        }else {
            ipUIDMap.put(ip, uid);
        }

        servletContext.setAttribute("ipUIDMap",ipUIDMap);
        logger.info(modbus.getUID()+"连接开启····");



    }

    public void sendWeb(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        wsUIDMap=(Map) servletContext.getAttribute("wsUIDMap");
        String UID = modbus.getUID();
        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setCmd("webLoginActor");
        webSocketMsg.setData();
        //遍历wsUIDMap获取所有监听UID的ws通道
        for(Map.Entry<String,String> entry:wsUIDMap.entrySet()){
            if(entry.getValue().equals(UID)){
                Channel incoming = (Channel)webSocketClient.get(entry.getKey());
                if(incoming!=null) {
                    incoming.writeAndFlush(new TextWebSocketFrame(new Date().toString()+"-终端" + modbus.getUID() + "上线"));
                }
            }
        }

    }

    public Map<String, String> getIpUIDMap() {
        return ipUIDMap;
    }

    public void setIpUIDMap(Map<String, String> ipUIDMap) {
        this.ipUIDMap = ipUIDMap;
    }

    public Map<String, Channel> getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(Map<String, Channel> webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public Map<String, Channel> getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Map<String, Channel> clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Map<String, String> getWsUIDMap() {
        return wsUIDMap;
    }

    public void setWsUIDMap(Map<String, String> wsUIDMap) {
        this.wsUIDMap = wsUIDMap;
    }
}
