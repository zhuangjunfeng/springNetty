package com.air.controller;

import ch.qos.logback.classic.Logger;
import com.air.constant.WxUrlType;
import com.air.entity.AccessTokenEntity;
import com.air.pojo.AirUser;
import com.air.pojo.AirWxInfo;
import com.air.service.AirUserService;
import com.air.util.WxUtil;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(WxMegController.class);
    @Resource
    private AirUserService airUserService;

    /**
     * 登录成功跳转
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginSys(HttpServletRequest request){
        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = (AirWxInfo)request.getSession().getServletContext().getAttribute("wxinfo");
        String code = request.getParameter("code");
        Map<String,String> params = new HashMap<String,String>();
        params.put("appid",airWxInfo.getAppid());
        params.put("secret",airWxInfo.getSecret());
        params.put("code",code);
        params.put("grant_type","authorization_code");
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity =WxUtil.sendRequest(WxUrlType.accessTokenUrl, HttpMethod.GET,params,null, AccessTokenEntity.class);
        AirUser airUser = new AirUser();
        Map<String,String> getUserInfoParams = new HashMap<String,String>();
        getUserInfoParams.put("access_token", accessTokenEntity.getAccess_token());
        getUserInfoParams.put("openid", accessTokenEntity.getOpenid());
        getUserInfoParams.put("lang","zh_CN");
        airUser = WxUtil.sendRequest(WxUrlType.userInfoUrl,HttpMethod.GET,getUserInfoParams,null,AirUser.class);
        if(airUser!=null) {
            airUserService.addAirUser(airUser);
        }
        request.getSession().setAttribute("airUser",airUser);
        String goUrl= (String)request.getSession().getAttribute("goUrl");
        if(goUrl!=null&&!goUrl.equals("")){
            return new ModelAndView("redirect:"+goUrl, null);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    /**
     * 微信登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/go", method = RequestMethod.GET)
    public String  goWx(HttpServletRequest request){
        AirWxInfo airWxInfo = new AirWxInfo();
        airWxInfo = (AirWxInfo)request.getSession().getServletContext().getAttribute("wxinfo");
        String appidUrl="?appid="+airWxInfo.getAppid()+"&";
        String redirect_uri="redirect_uri=http://air.semsplus.com/rest/wx/login&";
        String typeUrl ="response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return "redirect:"+WxUrlType.authorizeUrl+appidUrl+redirect_uri+typeUrl;
    }

    /**
     * 设备设置
     * @param request
     * @return
     */
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView Settings(HttpServletRequest request) {
        AirUser airUser = new AirUser();
        airUser=(AirUser)request.getSession().getAttribute("airUser");
        if(airUser==null){
            request.getSession().setAttribute("goUrl","/settings");
            return new ModelAndView("redirect:/go", null);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("settings");
        return mv;
    }
    /**
     * 售后服务
     * @param request
     * @return
     */
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public ModelAndView Service(HttpServletRequest request) {
        AirUser airUser = new AirUser();
        airUser=(AirUser)request.getSession().getAttribute("airUser");
        if(airUser==null){
            request.getSession().setAttribute("goUrl","/service");
            return new ModelAndView("redirect:/go", null);
        }
        logger.debug(airUser.getOpenid()+"");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("service");
        return mv;
    }
    /**
     * 设备监控
     * @param request
     * @return
     */
    @RequestMapping(value = "/monitoring", method = RequestMethod.GET)
    public ModelAndView Monitoring(HttpServletRequest request,String uid) {
        logger.debug("需要监控UID为："+uid);
        if(uid==null||uid.equals("")){
            return new ModelAndView("redirect:/go", null);
        }
        AirUser airUser = new AirUser();
        airUser=(AirUser)request.getSession().getAttribute("airUser");
        if(airUser==null){
            request.getSession().setAttribute("goUrl","/monitoring?uid="+uid);
            return new ModelAndView("redirect:/go", null);
        }
        logger.debug(airUser.getOpenid()+"");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("monitoring");
        return mv;
    }
    /**
     * 获取微信权限验证
     * @param signature 指纹
     * @param timestamp 时间戳
     * @param nonce 随机码
     * @param echostr 加密串
     * @return
     */
    @RequestMapping(method =RequestMethod.GET,produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String authGet(@RequestParam("signature") String signature,
                   @RequestParam("timestamp") String timestamp,
                   @RequestParam("nonce") String nonce,
                   @RequestParam("echostr") String echostr) {
        logger.info("微信验证"+echostr);
        return echostr;
    }

    /**
     *接收微信服务器发送的消息
     * @param requestBody
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String receiveData(@RequestBody String requestBody){

        return "";
    }



}
