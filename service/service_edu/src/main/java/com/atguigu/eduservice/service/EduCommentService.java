package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-17
 */
public interface EduCommentService extends IService<EduComment> {


    Map<String, Object> getCommentPage(Page<EduComment> page, QueryWrapper<EduComment> wrapper);

    R saveComment(EduComment comment, HttpServletRequest request);
}
