package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService)
    {
        try
        {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //课程分类列表(树形)
    @Override
    public List<OneSubject> getAllOneTwoSubject()
    {
        //1 查询所有一级分类 parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_Id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有二级分类 parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_Id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3 封装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++)
        {
            OneSubject oneSubject = new OneSubject();
            EduSubject eduSubject = oneSubjectList.get(i);
            //利用beanUtils进行封装 将eduSubject 相同的属性赋值到oneSubject中去
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++)
            {
                if (twoSubjectList.get(m).getParentId().equals(oneSubject.getId()))
                {
                    TwoSubject tSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoSubjectList.get(m),tSubject);
                    twoFinalSubjectList.add(tSubject);
                }
            }
            //二级封装
            oneSubject.setChildren(twoFinalSubjectList);
        }



        return finalSubjectList;
    }
}
