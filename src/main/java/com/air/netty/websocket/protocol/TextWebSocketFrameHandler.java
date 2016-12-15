package com.air.netty.websocket.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Map;

@Component
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends
		SimpleChannelInboundHandler<TextWebSocketFrame> {
	private ServletContext servletContext;
	private Map websocketMap;
	private Map clientMap;
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
								TextWebSocketFrame msg) throws Exception {
		Channel incoming = ctx.channel();
		WebSocketMsg webSocketMsg = new WebSocketMsg();
		JSONObject jsonObject = new JSONObject(msg.text());
		webSocketMsg.setCmd(jsonObject.get("cmd").toString());
		webSocketMsg.setData(jsonObject.get("data").toString());
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		ac.getBean(webSocketMsg.getCmd()).getClass().getConstructor(WebSocketMsg.class,Channel.class,ServletContext.class).newInstance(webSocketMsg,incoming,servletContext);
	}
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		websocketMap= (Map) servletContext.getAttribute("websocketMap");
		websocketMap.put("000000000001",incoming);
		servletContext.setAttribute("websocketMap",websocketMap);
		channels.add(incoming);
	}
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		websocketMap= (Map) servletContext.getAttribute("websocketMap");
		websocketMap.remove(incoming.remoteAddress().toString());
		servletContext.setAttribute("websocketMap",websocketMap);

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