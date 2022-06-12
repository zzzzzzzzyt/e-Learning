package com.atguigu.eduorder.client;

import com.atguigu.commonvo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu", fallback = EduClientImpl.class)
public interface EduClient {
    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    CourseOrderVo getCourseInfoOrder(@PathVariable("id") String id);
}
