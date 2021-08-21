package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-06
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //来查询前四个老师
    @Cacheable(value = "teacher",key = "'selectIndexList'") //将数据存放在redis中了 所以查 没有的话 放入 有的话 直接拿出来 就不用查了
    @Override
    public List<EduTeacher> listIndex()
    {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = baseMapper.selectList(queryWrapper);
        return eduTeachers;
    }


    //根据 前端分页显示教师数据
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher)
    {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher,wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
