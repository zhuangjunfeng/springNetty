package com.air.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/9
 **/
public class ModbusHander extends SimpleChannelInboundHandler<Object> {

    private ServletContext servletContext;

    public ModbusHander(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        Channel incoming = ctx.channel();

        if(obj instanceof Modbus) {
            Modbus msg = (Modbus)obj;

            Map<String,Channel> websocketMap=new HashMap<String,Channel>();

            websocketMap= (Map<String,Channel>) servletContext.getAttribute("websocketMap");

            for(Map.Entry<String,Channel> entry:websocketMap.entrySet()){
                entry.getValue().writeAndFlush(new TextWebSocketFrame(msg.getDATA()));
            }

            System.out.println("Client->Server:"+incoming.remoteAddress()+"  Data:"+msg.getDATA());
            incoming.write(obj);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        Map clientMap=new HashMap();
        clientMap= (Map) servletContext.getAttribute("clientMap");
        clientMap.put(incoming.remoteAddress().toString(),incoming);
        servletContext.setAttribute("clientMap",clientMap);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        Map clientMap=new HashMap();
        clientMap= (Map) servletContext.getAttribute("clientMap");
        clientMap.remove(incoming.remoteAddress().toString());
        servletContext.setAttribute("clientMap",clientMap);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
