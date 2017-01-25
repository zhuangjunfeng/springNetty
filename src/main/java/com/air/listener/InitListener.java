package com.air.listener;

import com.air.constant.WxUrlType;
import com.air.entity.AccessTokenEntity;
import com.air.pojo.AirWxInfo;
import com.air.service.AirWxInfoService;
import com.air.service.NettyService;
import com.air.service.WebSocketService;
import com.air.util.StringUtils;
import com.air.util.WxUtil;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.*;

/**
 * 内容管理平台服务启动监听器
 * 查询字典数据存入缓存
 * Created by Administrator on 2016/9/2.
 */
@Component
public class InitListener implements InitializingBean,ServletContextAware{
    private static Logger logger = Logger.getLogger(InitListener.class);

    @Resource
    private NettyService nettyService;
   @Resource
    private WebSocketService webSocketService;
    @Resource
    private AirWxInfoService airWxInfoService;
    public void  afterPropertiesSet() throws Exception{}
    public void setServletContext(ServletContext servletContext){
        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = airWxInfoService.queryWxInfo();
        servletContext.setAttribute("wxinfo",airWxInfo);
        Map<String,Channel> clientMap=new HashMap();
        Map<String,Channel> websocketMap=new HashMap();
        Map<String,String> ipUIDMap=new HashMap();
        Map<String,String> wsUIDMap = new HashMap();
        servletContext.setAttribute("ipUIDMap",ipUIDMap);
        servletContext.setAttribute("wsUIDMap",wsUIDMap);
        servletContext.setAttribute("clientMap",clientMap);
        servletContext.setAttribute("websocketMap",websocketMap);
        this.initAccessToken(servletContext);
        this.initNettyServer(servletContext);

    }

    public void initNettyServer(ServletContext servletContext){
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

    public void initAccessToken(ServletContext servletContext){
        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = (AirWxInfo)servletContext.getAttribute("wxinfo");
        Map<String,String> params = new HashMap<String,String>();
        params.put("grant_type","client_credential");
        params.put("appid",airWxInfo.getAppid());
        params.put("secret",airWxInfo.getSecret());
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity = WxUtil.sendRequest(WxUrlType.tokenUrl, HttpMethod.GET, params, null, AccessTokenEntity.class);
        servletContext.setAttribute("accessToken",accessTokenEntity);
        logger.info("获取accessToken为"+accessTokenEntity.getAccess_token());
    }



}
