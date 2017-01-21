package com.air.controller;

import com.air.constant.WxUrlType;
import com.air.entity.AccessTokenEntity;
import com.air.pojo.AirUser;
import com.air.pojo.AirWxInfo;
import com.air.service.AirUserService;
import com.air.util.WxUtil;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
    @Resource
    private AirUserService airUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginSys(HttpServletRequest request){

        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = (AirWxInfo)request.getSession().getServletContext().getAttribute("wxinfo");

        String code = request.getParameter("code");
        logger.info("获取微信code："+code);
        Map<String,String> params = new HashMap<String,String>();
        params.put("appid",airWxInfo.getAppid());
        params.put("secret",airWxInfo.getSecret());
        params.put("code",code);
        params.put("grant_type","authorization_code");
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity =WxUtil.sendRequest(WxUrlType.accessTokenUrl, HttpMethod.GET,params,null, AccessTokenEntity.class);
        logger.info("获取微信Tocken:"+ accessTokenEntity);
        AirUser airUser = new AirUser();
        Map<String,String> getUserInfoParams = new HashMap<String,String>();
        getUserInfoParams.put("access_token", accessTokenEntity.getAccess_token());
        getUserInfoParams.put("openid", accessTokenEntity.getOpenid());
        getUserInfoParams.put("lang","zh_CN");
        airUser = WxUtil.sendRequest(WxUrlType.userInfoUrl,HttpMethod.GET,getUserInfoParams,null,AirUser.class);
        logger.info("获取用户信息："+airUser);
        if(airUser!=null) {
            airUserService.addAirUser(airUser);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
    @RequestMapping(value = "/go", method = RequestMethod.GET)
    public String  goWx(HttpServletRequest request){
        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = (AirWxInfo)request.getSession().getServletContext().getAttribute("wxinfo");
        String appidUrl="?appid="+airWxInfo.getAppid()+"&";
        String redirect_uri="redirect_uri=http://air.semsplus.com/rest/wx/login&";
        String typeUrl ="response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return "redirect:"+WxUrlType.authorizeUrl+appidUrl+redirect_uri+typeUrl;
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
