package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zzyt
 * @since 2021-08-09
 */

public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getCoursePublishInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
