package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.handler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import com.atguigu.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("eduvod/video")
//@CrossOrigin
public class VodController
{
    @Autowired
    private VodService vodService;

    //上传视频的方法
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file)
    {
        String videoId = vodService.uploadAlyVideo(file);
        return R.ok().data("videoId",videoId);
    }

    //根据id删除视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id)
    {
        try
        {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new GuliException(20001,"删除失败");
        }
    }
    //根据id数组批量删除阿里云视频
    //传入多个视频的id值
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList")List<String> videoIdList)
    {
        vodService.removeVideos(videoIdList);
        return R.ok();
    }

    //根据视频id获取视频的播放凭证
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable("id")String id)
    {
        try
        {   //初始化对象
            DefaultAcsClient client = InitObject.initVodClient("LTAI5tFhqtHpAjYLVx1VMDRV", "4c8XZMOTM3713QihUMfXg7QBl18OD3");
            //获取request
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            //调用方法获得凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);

        }catch (Exception e)
        {
            throw new GuliException(20001,"获取视频播放凭证失败");
        }
    }
}
