package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("85")
public class QueryTimeTaskActor {
    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;

    public QueryTimeTaskActor(){}

    public QueryTimeTaskActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.queryTimeTask();
    }

    public void queryTimeTask(){

    }
}
