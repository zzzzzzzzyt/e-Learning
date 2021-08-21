package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-17
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String id, String memberIdByJwtToken);
}
