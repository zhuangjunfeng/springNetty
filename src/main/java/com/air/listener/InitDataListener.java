package com.air.listener;

import com.air.service.NettyService;
import com.air.service.WebSocketService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
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
@Component
public class InitDataListener  implements InitializingBean,ServletContextAware{
    @Resource
    private NettyService nettyService;
   @Resource
    private WebSocketService webSocketService;
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


        Thread client = new Thread(new Runnable(){
            public void run(){
                nettyService.start(Integer.parseInt(p.getProperty("client.port")),servletContext);
            }});
        client.start();

        Thread websocket = new Thread(new Runnable(){
            public void run(){
                webSocketService.start(Integer.parseInt(p.getProperty("websocket.port")),servletContext);
            }});
        websocket.start();






    }
}
