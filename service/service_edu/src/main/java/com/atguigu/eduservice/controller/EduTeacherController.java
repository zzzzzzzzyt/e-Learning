package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zzyt
 * @since 2021-08-06
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin//这是避免跨域问题的注解
public class EduTeacherController
{

    @Resource
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有数据并返回
     * @return
     */
    @GetMapping("findAll")
    public R findAll()
    {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public R removeById(@PathVariable("id")String id)
    {
        boolean flag = eduTeacherService.removeById(id);
        if (flag)
        {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 利用mp的插件进行分页操作
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable("current") Long current,@PathVariable("limit") Long limit)
    {
        Page<EduTeacher> page = new Page<>(current,limit);
        eduTeacherService.page(page,null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        return R.ok().data("total",total).data("records",records);
    }

    /**
     * 多条件组合查询并分页
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current")Long current,
                                  @PathVariable("limit")Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery)
    {
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //多条件组合查询  判断是否存在 进行条件拼接
        if (!StringUtils.isEmpty(name))
        {
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level))
        {
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin))
        {
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end))
        {
            wrapper.le("gmt_modified",end);
        }

        //使得根据添加日期降序排序
        wrapper.orderByDesc("gmt_create");

        eduTeacherService.page(teacherPage,wrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();

        return R.ok().data("total",total).data("records",records);
    }


    /**
     * 添加讲师
     * @param teacher
     * @return
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher)
    {
        boolean flag = eduTeacherService.save(teacher);
        if (flag)
        {
            return R.ok();
        }else {
            return R.error();
        }
    }


    /**
     * 通过id查询讲师信息
     * @param id
     * @return
     */
    @GetMapping("select/{id}")
    public R getByid(@PathVariable("id")Long id)
    {

        //try
        //{
        //    int i = 10/0;
        //} catch (Exception e)
        //{
        //    throw new GuliException(20001,"出错了！");
        //}

        EduTeacher teacher = eduTeacherService.getById(id);
        if (teacher==null)
        {
            return R.error();
        }
        else {
            return R.ok().data("teacher",teacher);
        }
    }


    /**
     * 通过id 修改讲师信息
     * @param teacher
     * @return
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher)
    {
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag)
        {
            return R.ok();
        }else {
            return R.error();
        }
     }
}

