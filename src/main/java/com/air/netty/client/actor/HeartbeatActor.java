package com.air.netty.client.actor;

import com.air.netty.client.protocol.Modbus;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * Created by Administrator on 2016/12/13.
 */
@Component("F1")
public class HeartbeatActor {
    private Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;

    public HeartbeatActor(){}

    public HeartbeatActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.heartbeat();
    }

    public void heartbeat(){
        modbus.setCODE("80");
        modbus.setDATA("F101");
        channel.writeAndFlush(modbus);
    }
}
