package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-09
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);


    List<OneSubject> getAllOneTwoSubject();
}
