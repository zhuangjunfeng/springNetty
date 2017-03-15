package com.air.netty.client.protocol;

import com.air.constant.WxUrlType;
import com.air.entity.AccessTokenEntity;
import com.air.entity.StautsMsgTemplateEntity;
import com.air.entity.WxRespCodeEntity;
import com.air.pojo.AirUserDevice;
import com.air.pojo.ConnectionRecord;
import com.air.service.AirDeviceService;
import com.air.service.ConnectionRecordService;
import com.air.util.StringUtils;
import com.air.util.WxUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    protected static final Logger logger = LoggerFactory.getLogger(ModbusHandler.class);

    private ServletContext servletContext;
    @Resource
    private ConnectionRecordService connectionRecordService;
    @Resource
    private AirDeviceService airDeviceService;

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
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        String ip =incoming.remoteAddress().toString();

        Map<String,String> ipUIDMap=new HashMap();
        Map<String,Channel> clientMap = new HashMap();

        ipUIDMap= (Map<String,String>) servletContext.getAttribute("ipUIDMap");
        clientMap = (Map<String,Channel>) servletContext.getAttribute("clientMap");


        if(ipUIDMap!=null){
            String UID=ipUIDMap.get(ip).toString();
            logger.info("设备"+UID+"连接断开····");
            if (!UID.equals("")) {
                if(clientMap!=null){
                    //清除UID-channel关系
                    clientMap.remove(UID);
                    servletContext.setAttribute("clientMap",clientMap);
                }
                //清除ip-UID关系
                ipUIDMap.remove(ip);
                servletContext.setAttribute("ipUIDMap",ipUIDMap);
                logger.info("获取的登录UID为：" + UID+airDeviceService);
                if(this.airDeviceService!=null){
                    List<AirUserDevice> list=airDeviceService.queryDeviceOpenid(UID);
                    logger.info("----查询结果数为："+list.size());
                    if(list!=null){
                        for(AirUserDevice airUserDevice:list){
                            this.sendWxMsg(airUserDevice.getOpenid(),UID);
                        }
                    }
                }
                logger.info("设备"+UID+"连接断开成功");
            }
        }



    }
    /**
     * 根据UID查询相关的openId发送设备上线提醒模版消息
     * https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
     */
    public void sendWxMsg(String openid,String UID){
        AccessTokenEntity accessToken = (AccessTokenEntity)servletContext.getAttribute("accessToken");
        StautsMsgTemplateEntity msgTemplateEntity= new StautsMsgTemplateEntity();
        msgTemplateEntity.setFirstData("请注意，你的设备已远程断开连接", "#173177");
        msgTemplateEntity.setKeyword1Data("车载净化器", "#173177");
        msgTemplateEntity.setKeyword2Data(UID, "#173177");
        msgTemplateEntity.setKeyword3Data("不在线", "#173177");
        String date=(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date());
        msgTemplateEntity.setKeyword4Data(date, "#173177");
        msgTemplateEntity.setKeyword5Data("断线中", "#173177");
        msgTemplateEntity.setRemarkData("点击详情查看车内空气质量哦！", "#173177");

        Map params = new HashMap();
        params.put("touser",openid);
        params.put("template_id","Ku3Kw7p5fGBsJknGoTfiAzaJpdWW9FU408wwfaUTJ0o");
        params.put("url","http://air.semsplus.com/rest/wx/go");


        Map<String,String> getParams = new HashMap<String,String>();
        getParams.put("access_token",accessToken.getAccess_token());

        params.put("data",msgTemplateEntity);
        String postData = StringUtils.MapToStr(params);

        try {
            WxRespCodeEntity wxRespCodeEntity = new WxRespCodeEntity();
            wxRespCodeEntity = WxUtil.sendRequest(WxUrlType.msgTemplateUrl, HttpMethod.POST, getParams, new StringEntity(postData, "UTF-8"), WxRespCodeEntity.class);
            logger.info("当前token为："+ accessToken.getAccess_token());
            logger.info("发送模版信息结果："+ wxRespCodeEntity.getErrcode());
        }catch (Exception e){
            logger.error("",e);
        }

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
