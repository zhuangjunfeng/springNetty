package com.air.netty.websocket.actor;

import com.air.netty.websocket.protocol.WebSocketMsg;
import com.air.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/14.
 */
@Controller
public class WebLoginActor {
    protected static final Logger logger = LoggerFactory.getLogger(WebLoginActor.class);

    private WebSocketMsg webSocketMsg;
    private Channel channel;
    private ServletContext servletContext;
    private Map<String,Channel> webSocketClient;
    private Map<String,String> wsUIDMap;
    private Map clientSocket;




    public WebLoginActor(){}

    public WebLoginActor(WebSocketMsg webSocketMsg,Channel incoming,ServletContext servletContext){
        this.webSocketMsg=webSocketMsg;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.login();
    }

    public void login(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        wsUIDMap=(Map) servletContext.getAttribute("wsUIDMap");
        String UID = webSocketMsg.getUid();
        String wsIP = channel.remoteAddress().toString();
        //缓存IP-UID关系
        wsUIDMap.put(wsIP,UID);
        servletContext.setAttribute("wsUIDMap", wsUIDMap);
        //缓存IP-WS关系
        webSocketClient.put(wsIP,channel);
        servletContext.setAttribute("websocketMap", webSocketClient);

        //查询设备在线状态
        clientSocket=(Map) servletContext.getAttribute("clientMap");
        Channel incoming = (Channel)clientSocket.get(UID);
        logger.info("监听设备："+webSocketMsg.getUid());

        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setUid(UID);
        webSocketMsg.setCmd("webLoginActor");
        if(incoming!=null) {
            webSocketMsg.setData("active");
            String rs= StringUtils.ObjectToJson(webSocketMsg);
            channel.writeAndFlush(new TextWebSocketFrame(rs));
        }else{
            webSocketMsg.setData("noActive");
            String rs= StringUtils.ObjectToJson(webSocketMsg);
            channel.writeAndFlush(new TextWebSocketFrame(rs));
        }
    }


    public Map getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Map clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Map<String, String> getWsUIDMap() {
        return wsUIDMap;
    }

    public void setWsUIDMap(Map<String, String> wsUIDMap) {
        this.wsUIDMap = wsUIDMap;
    }

    public Map<String, Channel> getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(Map<String, Channel> webSocketClient) {
        this.webSocketClient = webSocketClient;
    }
}
