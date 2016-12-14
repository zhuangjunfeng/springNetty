package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("8D")
public class QueryTimeActor {
    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;

    public QueryTimeActor(){}

    public QueryTimeActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.queryTime();
    }

    public void queryTime(){

    }
}
