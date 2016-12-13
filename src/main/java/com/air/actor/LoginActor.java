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
    public LoginActor(){}

    public LoginActor(Modbus modbus,Channel incoming,ServletContext servletContext){

    }
}
