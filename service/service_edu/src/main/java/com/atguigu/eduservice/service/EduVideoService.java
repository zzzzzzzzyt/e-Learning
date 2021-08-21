package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-09
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
