package com.air.listener;

import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/15.
 */
@Component
public class JobListerner implements ApplicationContextAware,ServletContextAware{
    private static Logger logger = Logger.getLogger(JobListerner.class);

    private ApplicationContext applicationContext;
    private ServletContext servletContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Scheduled(cron = "0/5 * * * * ? ")//每隔5秒
    public void run(){

        Map<String,Channel> websocketMap=(Map)servletContext.getAttribute("websocketMap");
        Map<String,Channel> clientMap=(Map)servletContext.getAttribute("clientMap");
        logger.info("当前客户端在线人数为："+websocketMap.size());
        logger.info("当前设备端在线人数为："+clientMap.size());
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
