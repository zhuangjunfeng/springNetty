package com.air.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/12
 **/
@Service
public interface WebSocketService {
    /**
     * Æô¶¯·þÎñÆ÷
     */
    public void start(int port, ServletContext servletContext);

}
