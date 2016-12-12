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
     * Æô¶¯·þÎñÆ÷
     */
    public void start(int port, ServletContext servletContext);

}
