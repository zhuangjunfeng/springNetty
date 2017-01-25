package com.air.netty.client.actor;

import com.air.constant.WxUrlType;
import com.air.entity.AccessTokenEntity;
import com.air.entity.WxRespCode;
import com.air.netty.client.protocol.Modbus;
import com.air.util.StringUtils;
import com.air.util.WxUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
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
    public void sendWxMsg(){
        AccessTokenEntity accessToken = (AccessTokenEntity)servletContext.getAttribute("accessToken");
        Map<String,String> params = new HashMap<String,String>();
        params.put("touser","o3aw6v5x9S36WOS0viwzp80QvP5o");
        params.put("template_id","Ku3Kw7p5fGBsJknGoTfiAzaJpdWW9FU408wwfaUTJ0o");
        params.put("url","http://air.semsplus.com/rest/wx/go");
        params.put("data","{" +
                            "\"first\":{"
                                        +"\"value\":"+ "\"你的设备已远程操作成功\","
                                        +"\"color\":"+ "\"#173177\""+
                                      "}," +
                                "\"keynote1\":{"
                                +"\"value\":"+ "\"0000000\","
                                +"\"color\":"+ "\"#173177\""+
                                "}," +
                                "\"keynote2\":{"
                                +"\"value\":"+ "\"0000000\","
                                +"\"color\":"+ "\"#173177\""+
                                "}," +
                                "\"keynote3\":{"
                                +"\"value\":"+ "\"0000000\","
                                +"\"color\":"+ "\"#173177\""+
                                "}," +
                                "\"keynote4\":{"
                                +"\"value\":"+ "\"0000000\","
                                +"\"color\":"+ "\"#173177\""+
                                "}," +
                                "\"keynote5\":{"
                                +"\"value\":"+ "\"0000000\","
                                +"\"color\":"+ "\"#173177\""+
                                "}," +
                                "\"remark\":{"
                                +"\"value\":"+ "\"设备上线\","
                                +"\"color\":"+ "\"#173177\""+
                                "}" +

                "}");
        WxRespCode wxRespCode=WxUtil.sendRequest(WxUrlType.msgTemplateUrl+accessToken.getAccess_token(), HttpMethod.POST,params,null, WxRespCode.class);
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
