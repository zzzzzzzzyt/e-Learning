package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ChapterVo
{
    private String id;
    private String title;
    private List<VideoVo> videoVoList = new ArrayList<>();
}
