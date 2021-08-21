package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-19
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
