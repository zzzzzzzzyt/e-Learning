package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin//避免跨域问题的注解 不用跨域了 因为在gateway中帮你跨域了 如果再跨域就会报错
public class EduLoginController
{
    @PostMapping("login")
    public R login()
    {
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info()
    {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wx3.sinaimg.cn/mw690/0076Fpdqly1gs56iq82nyj30g10q975o.jpg");
    }
}
