package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonvo.CourseOrderVo;
import com.atguigu.eduservice.client.OrdersClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController
{
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    //根据条件查询课程列表
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable("page")Long page, @PathVariable("limit")Long limit,
                                @RequestBody(required = false)CourseFrontVo courseFrontVo)
    {
        Page<EduCourse> eduCoursePage = new Page<>(page,limit);
        Map<String,Object> map =  courseService.getCourseFrontList(eduCoursePage,courseFrontVo);


        return R.ok().data((HashMap<String, Object>) map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId")String courseId, HttpServletRequest request)
    {
        //先查基本信息 然后再把课程的章节小节查出来
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //章节小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //查询课程是否被购买过
        //先判断有没有登录
        if (!StringUtils.isEmpty(JwtUtils.getMemberIdByJwtToken(request)))
        {
            boolean buyCourse = ordersClient.isBuyCourse(courseId,JwtUtils.getMemberIdByJwtToken(request));

            return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
        }

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",false);


    }


    //获取课程信息 通过远程调用给order模块使用
    @GetMapping("getCourseInfoOrder/{id}")
    public CourseOrderVo getCourseInfoOrder(@PathVariable("id")String id)
    {
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(id);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        BeanUtils.copyProperties(courseWebVo,courseOrderVo);
        return courseOrderVo;
    }

}
