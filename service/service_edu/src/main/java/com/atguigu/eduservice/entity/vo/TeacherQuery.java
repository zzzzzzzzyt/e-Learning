package com.atguigu.eduservice.entity.vo;

import lombok.Data;

//进行条件查询的条件封装对象
@Data
public class TeacherQuery {
    private String name;
    private Integer level;
    private String begin;
    private String end;
}
