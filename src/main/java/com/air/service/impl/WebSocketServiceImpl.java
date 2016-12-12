package com.air.service.impl;

import com.air.netty.websocket.HttpRequestHandler;
import com.air.netty.websocket.TextWebSocketFrameHandler;
import com.air.service.WebSocketService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/12
 **/
@Service
public class WebSocketServiceImpl  implements WebSocketService{
    private static Logger logger = Logger.getLogger(WebSocketServiceImpl.class);

    @Autowired
    private HttpRequestHandler httpRequestHandler;
    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;


    @Override
    public void start(int port, ServletContext servletContext) {
        httpRequestHandler.setWsUri("/ws");
        textWebSocketFrameHandler.setServletContext(servletContext);
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(httpRequestHandler);
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                            pipeline.addLast(textWebSocketFrameHandler);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            logger.info("websocket 启动了" + port);

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
            logger.info("websocket 关闭了");
        }
    }

}
