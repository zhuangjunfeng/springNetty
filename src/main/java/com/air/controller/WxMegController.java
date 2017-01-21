package com.air.controller;

import com.air.pojo.AirWxInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/12/15.
 */
@Controller
@RequestMapping("/wx")
public class WxMegController {

    private static Logger logger = Logger.getLogger(WxMegController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginSys(HttpServletRequest request){
        String code = request.getParameter("code");
        logger.info("获取微信code"+code);

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
