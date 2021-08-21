package com.atguigu.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan("com.atguigu.eduorder.mapper")  //因为要用到mapper 所以需要扫描
@EnableDiscoveryClient //要用到nacos 所以必须要填
public class OrderApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(OrderApplication.class,args);
    }
}
