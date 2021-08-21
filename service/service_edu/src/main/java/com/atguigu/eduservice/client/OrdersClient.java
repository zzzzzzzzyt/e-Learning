package com.atguigu.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-order",fallback = OrdersClientImpl.class)
public interface OrdersClient
{
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId")String courseId, @PathVariable("memberId")String memberId);
}
