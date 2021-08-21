package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.handler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zzyt
 * @since 2021-08-09
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Resource
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo)
    {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节

    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id)
    {
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        //这个需要一个视频的id 所以要先查出视频的id
        if (!StringUtils.isEmpty(video))
        {
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20001)
            {
                throw new GuliException(20001,"删除视频失败,熔断器....");
            }
        }

        videoService.removeById(id);
        return R.ok();
    }



    //要做到修改小节 有两步  第一步查出小节
    @GetMapping("getVideoInfo/{id}")
    public R getVideo(@PathVariable String id)
    {
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo)
    {
        boolean flag = videoService.updateById(eduVideo);
        if (flag)
        {
            return R.ok();
        }else {
            throw new GuliException(20001,"修改失败");
        }
    }

}

