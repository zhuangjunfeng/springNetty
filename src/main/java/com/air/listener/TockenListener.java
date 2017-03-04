package com.air.listener;

import com.air.util.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Administrator on 2017/1/21.
 */
@Component


public class TockenListener implements HttpSessionListener {
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(TockenListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        String tocken = StringUtils.getMD5(httpSessionEvent.getSession().getId());
        logger.info("生成Token:"+tocken);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        String tocken = StringUtils.getMD5(httpSessionEvent.getSession().getId());
        logger.info("移除Token:"+tocken);
    }


}
