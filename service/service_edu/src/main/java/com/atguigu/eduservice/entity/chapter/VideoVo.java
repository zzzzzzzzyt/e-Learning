package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

@Data
public class VideoVo
{
    private String id;

    private String title;

    private int isFree;

    private String videoSourceId;//视频id
}
