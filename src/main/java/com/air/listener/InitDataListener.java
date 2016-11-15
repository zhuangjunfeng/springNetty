package com.air.listener;

import com.air.netty.client.ProtocolServer;
import com.air.netty.websocket.WebsocketChatServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 内容管理平台服务启动监听器
 * 查询字典数据存入缓存
 * Created by Administrator on 2016/9/2.
 */
@Controller
public class InitDataListener  implements InitializingBean,ServletContextAware{

    public void  afterPropertiesSet() throws Exception{}
    public void setServletContext(ServletContext servletContext){
        Map clientMap=new HashMap();
        Map websocketMap=new HashMap();
        servletContext.setAttribute("clientMap",clientMap);
        servletContext.setAttribute("websocketMap",websocketMap);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config/netty.properties");
        Properties p = new Properties();
        try{
            p.load(inputStream);
        } catch (IOException e1){
            e1.printStackTrace();
        }
          WebsocketChatServer websocketChatServer=  new WebsocketChatServer(Integer.parseInt(p.getProperty("websocket.port")),servletContext);
          ProtocolServer protocolServer= new ProtocolServer(Integer.parseInt(p.getProperty("client.port")),servletContext);
        websocketChatServer.start();
        protocolServer.start();
    }
}
