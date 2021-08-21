package com.atguigu.eduservice.entity.vo;

import lombok.Data;

//进行条件查询的条件封装对象
@Data
public class CourseQuery
{
    private String title;
    private String status;
}
