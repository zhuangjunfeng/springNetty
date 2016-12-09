package com.air.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/9
 **/
public class ModbusEncoder extends MessageToByteEncoder<Modbus> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Modbus msg,
                          ByteBuf out) throws Exception {

    }
}
