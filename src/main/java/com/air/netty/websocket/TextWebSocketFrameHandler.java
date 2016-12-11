package com.air.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;


public class TextWebSocketFrameHandler extends
		SimpleChannelInboundHandler<TextWebSocketFrame> {
	private ServletContext servletContext;
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	public TextWebSocketFrameHandler( ServletContext servletContext){
		this.servletContext=servletContext;
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
								TextWebSocketFrame msg) throws Exception { // (1)
		Channel incoming = ctx.channel();

		Map<String,Channel> clientMap=new HashMap<String,Channel>();

		for (Channel channel : channels) {
			if (channel != incoming){
				channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
			} else {
				channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() ));
			}
		}
	}
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
		Channel incoming = ctx.channel();
		Map websocketMap=new HashMap();
		websocketMap= (Map) servletContext.getAttribute("websocketMap");
		websocketMap.put(incoming.remoteAddress().toString(),incoming);
		servletContext.setAttribute("websocketMap",websocketMap);
		channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
		channels.add(incoming);
//		System.out.println("Client:"+incoming.remoteAddress() +"加入");
	}
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
		Channel incoming = ctx.channel();
		Map websocketMap=new HashMap();
		websocketMap= (Map) servletContext.getAttribute("websocketMap");
		websocketMap.remove(incoming.remoteAddress().toString());
		servletContext.setAttribute("websocketMap",websocketMap);
		channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
//		System.out.println("Client:"+incoming.remoteAddress() +"离开");
		// A closed Channel is automatically removed from ChannelGroup,
		// so there is no need to do "channels.remove(ctx.channel());"
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
//		System.out.println("Client:"+incoming.remoteAddress()+"在线");
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
//		System.out.println("Client:"+incoming.remoteAddress()+"掉线");
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	// (7)
			throws Exception {
		Channel incoming = ctx.channel();
//		System.out.println("Client:"+incoming.remoteAddress()+"异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}
