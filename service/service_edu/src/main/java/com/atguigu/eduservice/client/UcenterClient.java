package com.atguigu.eduservice.client;

import com.atguigu.eduservice.entity.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable String id);
}
