package com.air.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.servlet.ServletContext;

/**
 * Websocket 聊天服务器-服务端
 * @author waylau.com
 * @date 2015-3-7
 */
public class WebsocketChatServer extends Thread {
    private ServletContext servletContext;
    private int port;

    public WebsocketChatServer(int port, ServletContext servletContext) {
        this.port = port;
        this.servletContext=servletContext;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new WebsocketChatServerInitializer(servletContext))  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            System.out.println("WebsocketChatServer 启动了" + port);

            // 绑定端口，开始接收进来的连接
            try {
                ChannelFuture f = b.bind(port).sync(); // (7)
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("WebsocketChatServer 关闭了");
        }

    }
}