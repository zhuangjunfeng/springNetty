package com.air.actor;

import com.air.netty.client.Modbus;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("80")
public class ReciveActor {
    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;

    public ReciveActor(){}

    public ReciveActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;

    }


}
