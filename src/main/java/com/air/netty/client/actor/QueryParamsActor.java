package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("8F")
public class QueryParamsActor {
    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;

    public QueryParamsActor(){}

    public QueryParamsActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.queryParams();
    }

    public void queryParams(){
        modbus.setCODE("8F");
        modbus.setDATA("");
        channel.write(modbus);
    }
}
