package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-13
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //查询所有banner
    @Cacheable(value = "banner", key = "'selectIndexList'")  //放在缓存中 redis 是以key-value进行存储的
    @Override
    public List<CrmBanner> getAllBanner() {
        //根据id进行降序排列 选取前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法进行拼接 sql语句 用来现在获得限制数目
        wrapper.last("limit 2");

        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);
        return bannerList;
    }
}
