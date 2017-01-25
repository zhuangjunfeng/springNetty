package com.air.netty.websocket.actor;

import com.air.constant.WxUrlType;
import com.air.entity.AccessTokenEntity;
import com.air.entity.StautsMsgTemplateEntity;
import com.air.entity.WxRespCodeEntity;
import com.air.netty.websocket.protocol.WebSocketMsg;
import com.air.util.StringUtils;
import com.air.util.WxUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/14.
 */
@Controller
public class WebLoginActor {
    private static Logger logger = Logger.getLogger(WebLoginActor.class);

    private WebSocketMsg webSocketMsg;
    private Channel channel;
    private ServletContext servletContext;
    private Map<String,Channel> webSocketClient;
    private Map<String,String> wsUIDMap;



    public WebLoginActor(){}

    public WebLoginActor(WebSocketMsg webSocketMsg,Channel incoming,ServletContext servletContext){
        this.webSocketMsg=webSocketMsg;
        this.channel=incoming;
        this.servletContext=servletContext;
        this.login();
        this.sendWxMsg();
    }

    public void login(){
        webSocketClient=(Map) servletContext.getAttribute("websocketMap");
        wsUIDMap=(Map) servletContext.getAttribute("wsUIDMap");
        String UID = webSocketMsg.getUid();
        String wsIP = channel.remoteAddress().toString();
        //缓存IP-UID关系
        wsUIDMap.put(wsIP,UID);
        servletContext.setAttribute("wsUIDMap", wsUIDMap);
        //缓存IP-WS关系
        webSocketClient.put(wsIP,channel);
        servletContext.setAttribute("websocketMap", webSocketClient);
        logger.info("监听设备："+webSocketMsg.getUid());
        channel.writeAndFlush(new TextWebSocketFrame("监听设备："+webSocketMsg.getUid()));
    }

    /**
     * 根据UID查询相关的openId发送设备上线提醒模版消息
     * https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
     */
    public void sendWxMsg(){
        AccessTokenEntity accessToken = (AccessTokenEntity)servletContext.getAttribute("accessToken");
        StautsMsgTemplateEntity msgTemplateEntity= new StautsMsgTemplateEntity();
        msgTemplateEntity.setFirst("请注意，你的设备已远程开启成功");
        msgTemplateEntity.setKeynote1("办公室1号报警器");
        msgTemplateEntity.setKeynote2("000000000000");
        msgTemplateEntity.setKeynote3("在线");
        msgTemplateEntity.setKeynote4("2017-02-25");
        msgTemplateEntity.setKeynote5("启动状态");
        msgTemplateEntity.setRemark("密切注意哦！");

        Map params = new HashMap();
        params.put("touser","o3aw6v5x9S36WOS0viwzp80QvP5o");
        params.put("template_id","Ku3Kw7p5fGBsJknGoTfiAzaJpdWW9FU408wwfaUTJ0o");
        params.put("url","http://air.semsplus.com/rest/wx/go");


        Map<String,String> getParams = new HashMap<String,String>();
        getParams.put("access_token",accessToken.getAccess_token());

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            params.put("data",objectMapper.writeValueAsString(msgTemplateEntity));
            String postData = StringUtils.MapToStr(params);

            WxRespCodeEntity wxRespCodeEntity = new WxRespCodeEntity();
            wxRespCodeEntity = WxUtil.sendRequest(WxUrlType.msgTemplateUrl, HttpMethod.POST, getParams, new StringEntity(postData), WxRespCodeEntity.class);
            logger.info("当前token为："+ accessToken.getAccess_token());
            logger.info("发送模版信息结果："+ wxRespCodeEntity.getErrcode());
        }catch (Exception e){
            logger.error(e);
        }

    }

    public Map<String, String> getWsUIDMap() {
        return wsUIDMap;
    }

    public void setWsUIDMap(Map<String, String> wsUIDMap) {
        this.wsUIDMap = wsUIDMap;
    }

    public Map<String, Channel> getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(Map<String, Channel> webSocketClient) {
        this.webSocketClient = webSocketClient;
    }
}
