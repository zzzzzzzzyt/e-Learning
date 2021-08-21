package com.atguigu.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.educenter.mapper")  //mapperscan没写好
public class UcenterApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
