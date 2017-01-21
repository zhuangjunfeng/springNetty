package com.air.controller;

import com.air.entity.AccessToken;
import com.air.pojo.AirWxInfo;
import com.air.util.WxUtil;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/15.
 */
@Controller
@RequestMapping("/wx")
public class WxMegController {

    private static Logger logger = Logger.getLogger(WxMegController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginSys(HttpServletRequest request){

        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = (AirWxInfo)request.getSession().getServletContext().getAttribute("wxinfo");

        String code = request.getParameter("code");
        logger.info("获取微信code"+code);
        Map<String,String> params = new HashMap<String,String>();
        params.put("appid",airWxInfo.getAppid());
        params.put("secret",airWxInfo.getSecret());
        params.put("code",code);
        params.put("grant_type","authorization_code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        AccessToken accessToken = new AccessToken();
        accessToken=WxUtil.sendRequest(url, HttpMethod.GET,params,null, AccessToken.class);

        logger.info("获取openID:"+accessToken.getOpenid());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
    @RequestMapping(value = "/go", method = RequestMethod.GET)
    public String  goWx(HttpServletRequest request){
        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = (AirWxInfo)request.getSession().getServletContext().getAttribute("wxinfo");
        String wxUrl="https://open.weixin.qq.com/connect/oauth2/authorize?";
        String appidUrl="appid="+airWxInfo.getAppid()+"&";
        String redirect_uri="redirect_uri=http://air.semsplus.com/rest/wx/login&";
        String typeUrl ="response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return "redirect:"+wxUrl+appidUrl+redirect_uri+typeUrl;
    }

    @RequestMapping(method =RequestMethod.GET,produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String authGet(@RequestParam("signature") String signature,
                   @RequestParam("timestamp") String timestamp,
                   @RequestParam("nonce") String nonce,
                   @RequestParam("echostr") String echostr) {
        logger.info("微信验证"+echostr);
        return echostr;
    }



}
