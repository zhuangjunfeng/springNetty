package com.air.netty.client.protocol;

import com.air.pojo.ConnectionRecord;
import com.air.service.ConnectionRecordService;
import com.air.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/9
 **/
@Component
@ChannelHandler.Sharable
public class ModbusHandler extends SimpleChannelInboundHandler<Object> {
    private static Logger logger = Logger.getLogger(ModbusHandler.class);

    private ServletContext servletContext;
    @Resource
    private ConnectionRecordService connectionRecordService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
            throws Exception {
        Channel incoming = ctx.channel();
        String incomingIp = "";
        String incomingPort = "";
        if(obj instanceof Modbus) {
            Modbus msg = (Modbus)obj;
            StringBuffer  data = new StringBuffer();
            data.append(msg.getHEADER_BEGIN());
            data.append(msg.getUID());
            data.append(msg.getHEADER_END());
            data.append(msg.getCODE());
            data.append(msg.getLEN());
            data.append(msg.getDATA());

            //CRC校验
            if(StringUtils.checkCRC(data.toString(), msg.getCRC())){
                String agreement=msg.getHEADER_BEGIN()
                        +msg.getUID()
                        +msg.getHEADER_END()
                        +msg.getCODE()+msg.getLEN()
                        +msg.getDATA()+msg.getCRC()
                        +msg.getFOOTER();

                String address=incoming.remoteAddress().toString();
                String regex="/(.*?):(.*)";
                Pattern p= Pattern.compile(regex);
                Matcher m=p.matcher(address);
                while(m.find()){
                    incomingIp=m.group(1);
                    incomingPort=m.group(2);
                }
                ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                ac.getBean(msg.getCODE()).getClass().getConstructor(Modbus.class,Channel.class,ServletContext.class).newInstance(msg,incoming,servletContext);

                ConnectionRecord connectionRecord = new ConnectionRecord();
                connectionRecord.setRecord_ip(incomingIp);
                connectionRecord.setRecord_port(incomingPort);
                connectionRecord.setRecord_agreement(agreement);
                connectionRecordService.saveRecord(connectionRecord);

            }
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

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
