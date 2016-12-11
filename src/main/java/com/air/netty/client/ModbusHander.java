package com.air.netty.client;

import com.air.pojo.ConnectionRecord;
import com.air.service.ConnectionRecordService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/9
 **/
public class ModbusHander extends SimpleChannelInboundHandler<Object> {

    private ServletContext servletContext;
    ConnectionRecord connectionRecord;
    String agreement,incomingIp,incomingPort;
    @Resource
    private ConnectionRecordService connectionRecordService;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
            agreement=msg.getHEADER_BEGIN()
                    +msg.getUID()
                    +msg.getHEADER_END()
                    +msg.getCODE()+msg.getLEN()
                    +msg.getDATA()+msg.getCRC()
                    +msg.getFOOTER();
//            connectionRecord.setRecord_creat_time(df.format(new Date()));
//            connectionRecord.setRecord_ip(incoming.localAddress().toString());
            String address=incoming.remoteAddress().toString();
            System.out.println(address);

            String regex="/(.*?):(.*)";
            Pattern p= Pattern.compile(regex);
            Matcher m=p.matcher(address);
            while(m.find()){
                incomingIp=m.group(1);
                incomingPort=m.group(2);
            }
            connectionRecord.setRecord_ip(incomingIp);
            connectionRecord.setRecord_port(incomingPort);
            connectionRecord.setRecord_agreement(agreement);
            connectionRecordService.saveRecord(connectionRecord);
//            System.out.println("Client->Server:" + incoming.remoteAddress() + msg.getBody());
//            incoming.write(obj);

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
