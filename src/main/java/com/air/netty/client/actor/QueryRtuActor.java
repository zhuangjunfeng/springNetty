package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("01")
public class QueryRtuActor {
    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;

    public QueryRtuActor(){}

    public QueryRtuActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        if(modbus.getDATA().substring(0,2).equals("28")){

        }
        switch (modbus.getDATA().substring(0,2)){
            case "28" :
                this.querySensor();
                break;
            case "29" :
                this.queryStatus();
                break;
        }
    }

    public void querySensor(){

    }
    public void queryStatus(){

    }
}
