package com.air.listener;

import com.air.constant.WxUrlType;
import com.air.entity.AccessTokenEntity;
import com.air.pojo.AirWxInfo;
import com.air.util.WxUtil;
import io.netty.channel.Channel;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/15.
 */
@Component
public class JobListerner implements ApplicationContextAware,ServletContextAware{
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(JobListerner.class);

    private ApplicationContext applicationContext;
    private ServletContext servletContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    public void run(){

        Map<String,Channel> websocketMap=(Map)servletContext.getAttribute("websocketMap");
        Map<String,Channel> clientMap=(Map)servletContext.getAttribute("clientMap");
        logger.info("当前客户端在线数为："+websocketMap.size());
        logger.info("当前设备端在线数为："+clientMap.size());
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void RefreshAccessToken(){
        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = (AirWxInfo)servletContext.getAttribute("wxinfo");
        Map<String,String> params = new HashMap<String,String>();
        params.put("grant_type","client_credential");
        params.put("appid",airWxInfo.getAppid());
        params.put("secret",airWxInfo.getSecret());
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity = WxUtil.sendRequest(WxUrlType.tokenUrl, HttpMethod.GET, params, null, AccessTokenEntity.class);
        servletContext.setAttribute("accessToken",accessTokenEntity);
        logger.info("刷新accessToken为"+accessTokenEntity.getAccess_token());
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
