package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController
{
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前八条热门课程  查询前四条名师
    @GetMapping("index")
    public R index()
    {
        //先是查询签八条课程 根据id来排序
        List<EduCourse> courseList = courseService.listIndex();
        //再查询前四条名师
        List<EduTeacher> teacherList = teacherService.listIndex();

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
