package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zzyt
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;


    //生成微信支付二维码接口
    //参数是订单号
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo") String orderNo) {
        //返回信息，包含二维码地址，还有其他信息
        Map map = payLogService.createNative(orderNo);
        return R.ok().data((HashMap<String, Object>) map);
    }

    //查询订单的状态 参数是订单号
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable("orderNo") String orderNo) {
        //根据订单号查询订单的支付情况
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        //如果查出来为空则直接返回错误 否则判断订单状态是否在支付
        if (map == null) {
            return R.error().message("支付出错了");
        }
        //如果不为空则查询状态
        if (map.get("trade_state").equals("SUCCESS"))  //这代表支付成功
        {
            //成功后修改status 同时在成功列表中添加一个记录
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");

        }

        //否则则代表正在支付
        return R.ok().code(25000).message("正在支付中~");


    }
}

