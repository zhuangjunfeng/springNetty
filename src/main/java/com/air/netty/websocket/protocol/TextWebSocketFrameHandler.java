package com.air.netty.websocket.protocol;

import com.air.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

@Component
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends
		SimpleChannelInboundHandler<TextWebSocketFrame> {
	private static Logger logger = Logger.getLogger(TextWebSocketFrameHandler.class);

	private ServletContext servletContext;
	private Map websocketMap;
	private Map clientMap;
	public ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
								TextWebSocketFrame msg) throws Exception {
		Channel incoming = ctx.channel();
		WebSocketMsg webSocketMsg = new WebSocketMsg();
		webSocketMsg = StringUtils.JsonToObject(msg.text());
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		ac.getBean(webSocketMsg.getCmd()).getClass().getConstructor(WebSocketMsg.class,Channel.class,ServletContext.class).newInstance(webSocketMsg,incoming,servletContext);
	}
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		channels.add(incoming);
	}
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		String ip =incoming.remoteAddress().toString();

		Map<String,String> wsUIDMap = new HashMap();
		Map<String,Channel> websocketMap = new HashMap();
		logger.info(ip+"-ws客户端离开了");
		websocketMap=(Map) servletContext.getAttribute("websocketMap");
		wsUIDMap=(Map) servletContext.getAttribute("wsUIDMap");
		//清除ip-UID关系
		wsUIDMap.remove(ip);
		//清除ip-channel关系
		websocketMap.remove(ip);
		servletContext.setAttribute("websocketMap",websocketMap);
		servletContext.setAttribute("wsUIDMap",wsUIDMap);
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Channel incoming = ctx.channel();
		cause.printStackTrace();
		ctx.close();
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public Map getWebsocketMap() {
		return websocketMap;
	}

	public void setWebsocketMap(Map websocketMap) {
		this.websocketMap = websocketMap;
	}

	public Map getClientMap() {
		return clientMap;
	}

	public void setClientMap(Map clientMap) {
		this.clientMap = clientMap;
	}
}
