package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zzyt
 * @since 2021-08-09
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {
    @Resource
    private EduCourseService eduCourseService;


    //课程列表 基本实现
    //TODO 完善条件查询带分页
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") Long current,
                                  @PathVariable("limit") Long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> coursePage = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        //多条件组合查询  判断是否存在 进行条件拼接
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        //使得根据添加日期降序排序
        wrapper.orderByDesc("gmt_create");

        eduCourseService.page(coursePage, wrapper);
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();

        return R.ok().data("records", records).data("total", total);
    }


    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfo courseInfo) {
        String courseId = eduCourseService.saveCourseInfo(courseInfo);
        return R.ok().data("courseID", courseId);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfo courseInfo = eduCourseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfo", courseInfo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfo courseInfo) {
        eduCourseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishInfo = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("coursePublishInfo", coursePublishInfo);
    }

    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse finalCourse = new EduCourse();
        finalCourse.setId(id);
        finalCourse.setStatus("Normal");
        eduCourseService.updateById(finalCourse);
        return R.ok();
    }

    @DeleteMapping("removeCourse/{courseId}")
    public R removeCourse(@PathVariable String courseId) {
        //删除小节 删除章节 删除描述 删除课程 按顺序进行删除
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }


}

