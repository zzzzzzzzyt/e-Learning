package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-09
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    CoursePublishVo getPublishCourseInfo(String id);

    void removeCourse(String courseId);

    List<EduCourse> listIndex();

    Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
