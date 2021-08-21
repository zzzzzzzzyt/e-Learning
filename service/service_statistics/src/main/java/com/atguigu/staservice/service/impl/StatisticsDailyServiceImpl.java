package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcentClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-19
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcentClient ucentClient;



    @Override
    public void registerCount(String day)
    {
        //添加数据之前 先删除相同日期的数据  这是个完善 这为了 不会重复的添加相同日期的数据 为了不用去筛选
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);


        //远程调用获取注册人数
        R registerR = ucentClient.countRegister(day);
        Integer registerNum = (Integer) registerR.getData().get("count");
        System.out.println(registerNum);
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象 将信息封存进去 然后加入数据库  把获取对象添加进数据库 表中
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);//统计日期

        baseMapper.insert(daily);
    }


    //获取展示的数据 进行展示 返回map集合
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end)
    {
        //进行查询 数据 封装进List
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> statisticsDailiesList = baseMapper.selectList(wrapper);

        //将获取的数据分别存入两个list  因为前端需要分别获取 两个json数组 而只有后端list才能对应前端的json数组
        List<String> date_calculatedList = new ArrayList();
        List<Integer> NumDataList = new ArrayList();
        for (StatisticsDaily daily : statisticsDailiesList)
        {
            date_calculatedList.add(daily.getDateCalculated());
            switch (type){
                case "register_num":
                    NumDataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    NumDataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    NumDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    NumDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("NumDataList",NumDataList);

        return map;
    }
}
