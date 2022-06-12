package com.atguigu.eduorder.client;

import com.atguigu.commonvo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/educenter/member/getUserInfoOrder/{id}")
    UcenterMemberVo getMemberInfoOrder(@PathVariable("id") String id);
}
