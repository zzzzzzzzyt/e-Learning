package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-17
 */
public interface PayLogService extends IService<PayLog> {
    //创建微信支付二维码
    Map createNative(String orderNo);

    //查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //修改订单的数据
    void updateOrderStatus(Map<String, String> map);
}
