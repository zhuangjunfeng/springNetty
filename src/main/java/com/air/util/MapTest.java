package com.air.util;


import com.air.constant.CmdType;
import com.air.entity.StautsMsgTemplateEntity;
import com.air.pojo.AirUser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description
 * @Author semstouch
 * @Date 2017/1/20
 **/
public class MapTest {
    public static void main(String[] args) {

        StautsMsgTemplateEntity msgTemplateEntity= new StautsMsgTemplateEntity();
        msgTemplateEntity.setFirstData("请注意，你的设备已远程开启成功","#173177");
        msgTemplateEntity.setKeynote1Data("办公室1号报警器","#173177");
        msgTemplateEntity.setKeynote2Data("000000000000", "#173177");
        msgTemplateEntity.setKeynote3Data("在线", "#173177");
        msgTemplateEntity.setKeynote4Data("2017-02-25", "#173177");
        msgTemplateEntity.setKeynote5Data("启动状态","#173177");
        msgTemplateEntity.setRemarkData("密切注意哦！", "#173177");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String rs=objectMapper.writeValueAsString(msgTemplateEntity);
            System.out.print(rs);
        }catch (Exception e){

        }


    }
}
