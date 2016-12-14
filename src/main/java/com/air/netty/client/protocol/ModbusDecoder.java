package com.air.netty.client.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Description
 * @Author semstouch
 * @Date 2016/12/9
 **/
public class ModbusDecoder extends LengthFieldBasedFrameDecoder {
    public ModbusDecoder(int maxFrameLength, int lengthFieldOffset,
                           int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
                lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Modbus decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if (in == null) {
            return null;
        }

        //数据解码成16进制字符串
        int len = in.readableBytes();
        ByteBuf buf = in.readBytes(len);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String msgStr = byte2HexStr(req);
        int msgStrLen = msgStr.length();


        //构造协议对象
        Modbus msg = new Modbus();
        msg.setHEADER_BEGIN(msgStr.substring(0,2));
        msg.setUID(msgStr.substring(2,14));
        msg.setHEADER_END(msgStr.substring(14,16));
        msg.setCODE(msgStr.substring(16,18));
        msg.setLEN(msgStr.substring(18,20));
        msg.setDATA(msgStr.substring(20,msgStrLen-4));
        msg.setCRC(msgStr.substring(msgStrLen-4,msgStrLen-2));
        msg.setFOOTER(msgStr.substring(msgStrLen-2));

        //判断数据长度是否正确
        if((Integer.parseInt(msg.getLEN())*2)!=msg.getDATA().length()){
            return null;
        }
        return msg;
    }

    /**
     * bytes转换成十六进制字符串
     * @param byte[] b byte数组
     * @return String
     */
    public static String byte2HexStr(byte[] b)
    {
        String stmp="";
        StringBuilder sb = new StringBuilder("");
        for (int n=0;n<b.length;n++)
        {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length()==1)? "0"+stmp : stmp);
        }
        return sb.toString().toUpperCase().trim();
    }
}
