package com.air.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @Author semstouch
 * @Date 2017/1/20
 **/
public class MapTest {
    public static void main(String[] args) {
        Map wsUIDMap = new HashMap();
        wsUIDMap.put("111","1223");
        wsUIDMap.put("112","1224");

        Map rs= wsUIDMap;
        rs.remove(null);


        System.out.print(wsUIDMap.get("111"));



    }
}
