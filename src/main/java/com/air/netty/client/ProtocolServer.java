/**
 *
 */
package com.air.netty.client;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;

/**
 * 说明：自定义协议服务端
 */
public class ProtocolServer extends Thread {
    private static Logger logger = Logger.getLogger(ProtocolServer.class);

    private int port;
    private ServletContext servletContext;
    private static final int MAX_FRAME_LENGTH = 1024 * 1024;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_FIELD_OFFSET = 6;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    /**
     *
     */
    public ProtocolServer(int port,ServletContext servletContext) {
        this.port = port;
        this.servletContext=servletContext;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast("decoder",
                                    new ProtocolDecoder(MAX_FRAME_LENGTH,
                                            LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
                                            LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));
                            ch.pipeline().addLast("encoder", new ProtocolEncoder());
                            ch.pipeline().addLast(new ProtocolServerHandler(servletContext));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，开始接收进来的连接
            try {
                ChannelFuture    f = b.bind(port).sync();
                logger.info("clientServer 启动了" + port);
                f.channel().closeFuture().sync();
            }catch (Exception e){
                e.printStackTrace();
            }
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.info("clientServer 关闭了");
        }
    }

}
