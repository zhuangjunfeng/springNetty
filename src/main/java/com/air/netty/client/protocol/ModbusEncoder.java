package com.air.netty.client.protocol;

import com.air.util.StringUtils;
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
        if (msg == null) {
            throw new Exception("The encode message is null");
        }

        StringBuffer data = new StringBuffer();
        data.append("68");
        data.append(msg.getUID());
        data.append("68");
        data.append(msg.getCODE());
        data.append(StringUtils.getHexString2(msg.getDATA().length()/2));
        data.append(msg.getDATA());
        byte[]  dataByte= StringUtils.hexStringToBytes(data.toString());
        byte crc = StringUtils.getCRC(dataByte);
        String crcStr = StringUtils.byteToHexString(crc);
        data.append(crcStr);
        data.append("16");
        out.writeBytes(StringUtils.hexStringToBytes(data.toString()));
    }
}
