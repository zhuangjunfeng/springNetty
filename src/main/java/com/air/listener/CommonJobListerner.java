package com.air.listener;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/15.
 */
@Component
public class CommonJobListerner {
	

    @Scheduled(cron = "0 0/1 * * * ? ")//每隔1秒隔行一次
    public void run(){

    }
}
