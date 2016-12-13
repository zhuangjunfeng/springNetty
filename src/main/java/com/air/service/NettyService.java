package com.air.service;

import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/12
 **/
@Service
public interface NettyService{
    /**
     * 启动服务器
     */
    public void start(int port, ServletContext servletContext);

}
