package com.air.netty.client.actor;

import com.air.constant.WxUrlType;
import com.air.entity.AccessTokenEntity;
import com.air.entity.StautsMsgTemplateEntity;
import com.air.entity.WxRespCodeEntity;
import com.air.netty.client.protocol.Modbus;
import com.air.pojo.AirUserDevice;
import com.air.service.AirDeviceService;
import com.air.util.StringUtils;
import com.air.util.WxUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.air.netty.websocket.protocol.WebSocketMsg;
/**
 * @Description  终端登录
 * @Author semstouch
 * @Date 2016/12/13
 **/
@Component("F0")
public class LoginActor{
    private static Logger logger = Logger.getLogger(LoginActor.class);
    private  Modbus modbus;
    private Channel channel;
    private ServletContext servletContext;
    @Resource
    private AirDeviceService airDeviceService;
    private Map<String,Channel> webSocketClient;
    private Map<String,Channel> clientSocket;
    private Map<String,String> ipUIDMap;
    private Map<String,String> wsUIDMap;

    public LoginActor(){}

    public LoginActor(Modbus modbus,Channel incoming,ServletContext servletContext){
        this.modbus=modbus;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.login();
        this.sendWeb();

        String UID = this.modbus.getUID();
        logger.info("获取的登录UID为："+UID);

        List<AirUserDevice> list=airDeviceService.queryDeviceOpenid(UID);
        logger.info("----查询结果数为："+list.size());
        if(list!=null){
            for(AirUserDevice airUserDevice:list){
                this.sendWxMsg(airUserDevice.getOpenid());
            }
        }
    }

    public void login(){
        modbus.setCODE("80");
        modbus.setDATA("F001");
        String uid =modbus.getUID();
        channel.writeAndFlush(modbus);


        //UID-channel关系缓存
        clientSocket=(Map) servletContext.getAttribute("clientMap");
        if(clientSocket==null) {
            clientSocket=new HashMap();
            clientSocket.put(uid, channel);
        }else {
            clientSocket.put(uid, channel);
        }
        servletContext.setAttribute("clientMap", clientSocket);


        //UID-IP关系缓存
        ipUIDMap= (Map) servletContext.getAttribute("ipUIDMap");
        String ip = channel.remoteAddress().toString();
        if(ipUIDMap==null){
            ipUIDMap=new HashMap();
            ipUIDMap.put(ip,uid);
        }else {
            ipUIDMap.put(ip, uid);
        }

        servletContext.setAttribute("ipUIDMap",ipUIDMap);
        logger.info(modbus.getUID()+"连接开启····");
    }

    public void sendWeb(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        wsUIDMap=(Map) servletContext.getAttribute("wsUIDMap");
        String UID = modbus.getUID();
        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setCmd("webLoginActor");
        webSocketMsg.setUid(modbus.getUID());
        webSocketMsg.setData(modbus.getDATA());
        ObjectMapper objectMapper = new ObjectMapper();
        //遍历wsUIDMap获取所有监听UID的ws通道
        for(Map.Entry<String,String> entry:wsUIDMap.entrySet()){
            if(entry.getValue().equals(UID)){
                Channel incoming = (Channel)webSocketClient.get(entry.getKey());
                if(incoming!=null) {
                    String rs= StringUtils.ObjectToJson(webSocketMsg);
                    incoming.writeAndFlush(new TextWebSocketFrame(rs));
                }
            }
        }

    }

    /**
     * 根据UID查询相关的openId发送设备上线提醒模版消息
     * https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
     */
    public void sendWxMsg(String openid){

        AccessTokenEntity accessToken = (AccessTokenEntity)servletContext.getAttribute("accessToken");
        StautsMsgTemplateEntity msgTemplateEntity= new StautsMsgTemplateEntity();
        msgTemplateEntity.setFirstData("请注意，你的设备已远程开启成功", "#173177");
        msgTemplateEntity.setKeyword1Data("车载净化器", "#173177");
        msgTemplateEntity.setKeyword2Data(modbus.getUID(), "#173177");
        msgTemplateEntity.setKeyword3Data("在线", "#173177");
        String date=(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date());
        msgTemplateEntity.setKeyword4Data(date, "#173177");
        msgTemplateEntity.setKeyword5Data("启动中", "#173177");
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
            wxRespCodeEntity = WxUtil.sendRequest(WxUrlType.msgTemplateUrl, HttpMethod.POST,getParams, new StringEntity(postData,"UTF-8"), WxRespCodeEntity.class);
            logger.info("当前token为："+ accessToken.getAccess_token());
            logger.info("发送模版信息结果："+ wxRespCodeEntity.getErrcode());
        }catch (Exception e){
            logger.error(e);
        }

    }

    public Map<String, String> getIpUIDMap() {
        return ipUIDMap;
    }

    public void setIpUIDMap(Map<String, String> ipUIDMap) {
        this.ipUIDMap = ipUIDMap;
    }

    public Map<String, Channel> getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(Map<String, Channel> webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public Map<String, Channel> getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Map<String, Channel> clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Map<String, String> getWsUIDMap() {
        return wsUIDMap;
    }

    public void setWsUIDMap(Map<String, String> wsUIDMap) {
        this.wsUIDMap = wsUIDMap;
    }
}
