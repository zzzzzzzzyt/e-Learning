package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-17
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;


    //分页查询所有的评论
    @Override
    public Map<String, Object> getCommentPage(Page<EduComment> page, QueryWrapper<EduComment> wrapper)
    {
        baseMapper.selectPage(page,wrapper);
        List<EduComment> commentList = page.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", page.getCurrent());
        map.put("pages", page.getPages());
        map.put("size", page.getSize());
        map.put("total", page.getTotal());
        map.put("hasNext", page.hasNext());
        map.put("hasPrevious", page.hasPrevious());

        return map;
    }


    //将评论存入数据库中
    @Override
    public R saveComment(EduComment comment, HttpServletRequest request)
    {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId))
        {
            return R.error().message("请先登录");
        }
        comment.setMemberId(memberId);
        String avatar = ucenterClient.getInfo(memberId).getAvatar();
        String nickName = ucenterClient.getInfo(memberId).getNickname();
        comment.setAvatar(avatar);
        comment.setNickname(nickName);

        baseMapper.insert(comment);
        return R.ok();
    }
}
