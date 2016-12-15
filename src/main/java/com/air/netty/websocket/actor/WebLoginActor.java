package com.air.netty.websocket.actor;

import com.air.netty.websocket.protocol.WebSocketMsg;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/14.
 */
@Controller
public class WebLoginActor {
    private WebSocketMsg webSocketMsg;
    private Channel channel;
    private ServletContext servletContext;
    private Map webSocketClient;



    public WebLoginActor(){}

    public WebLoginActor(WebSocketMsg webSocketMsg,Channel incoming,ServletContext servletContext){
        this.webSocketMsg=webSocketMsg;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.login();
    }

    public void login(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        webSocketClient.put(webSocketMsg.getData(),channel);
        channel.writeAndFlush(new TextWebSocketFrame("监听设备："+webSocketMsg.getData()));
    }
    public WebSocketMsg getWebSocketMsg() {
        return webSocketMsg;
    }

    public void setWebSocketMsg(WebSocketMsg webSocketMsg) {
        this.webSocketMsg = webSocketMsg;
    }
}
