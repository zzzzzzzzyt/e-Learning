package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zzyt
 * @since 2021-08-19
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //查询某天注册人数  然后在数据库中添加
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable("day") String day) {
        statisticsDailyService.registerCount(day);
        return R.ok();
    }


    //图标显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("getShowData/{type}/{begin}/{end}")
    public R getShowData(@PathVariable("type") String type, @PathVariable("begin") String begin,
                         @PathVariable("end") String end) {
        Map<String, Object> map = statisticsDailyService.getShowData(type, begin, end);
        return R.ok().data((HashMap<String, Object>) map);
    }
}

