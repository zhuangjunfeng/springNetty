package com.air.actor;

import com.air.netty.client.Modbus;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * @Description  ÖÕ¶ËµÇÂ¼
 * @Author semstouch
 * @Date 2016/12/13
 **/
@Component("F0")
public class LoginActor {
    private  Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;

    public LoginActor(){}

    public LoginActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.login();
    }

    public void login(){
        modbus.setCODE("80");
        modbus.setDATA("F001");
        channel.write(modbus);
    }
}
