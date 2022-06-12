package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zzyt
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据课程id还有cookie生成订单信息 存入数据库中
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("createOrder/{id}")
    public R saveOrder(@PathVariable("id") String id, HttpServletRequest request) {
        String orderId = orderService.saveOrder(id, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }


    //根据课程id获取课程信息 用于在页面上展示信息
    @GetMapping("getOrderInfo/{id}")
    public R getOrderInfo(@PathVariable("id") String id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", id);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    //根据课程id和用户id查询课程是否已经购买
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        if (count > 0) {

            System.out.println("查到了");
            return true;
        } else {
            return false;
        }

    }

}

