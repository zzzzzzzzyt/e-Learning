package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Schedule {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //每天的凌晨一点查询前一天的记录
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
