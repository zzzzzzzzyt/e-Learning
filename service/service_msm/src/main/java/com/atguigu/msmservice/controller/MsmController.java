package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


//通过向邮箱发送验证码 来代替发短信 因为我注册不上 阿里云短信 cao
@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送邮箱验证码的方法
    @GetMapping("send/{mailAdress}")
    public R sendMsm(@PathVariable("mailAdress") String mailAdress) {
        //1 从redis获取验证码，如果获取到 直接返回
        String code = redisTemplate.opsForValue().get(mailAdress);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //2 如果redis获取不到，进行阿里云发送
        //生成随机值 传递阿里云进行发送
        code = RandomUtils.generateRandom(4);
        //调用service发短信息的方法
        boolean isSend = msmService.send(code, mailAdress);
        if (isSend) {
            //发送成功，把发送成功验证码放到redis里面
            redisTemplate.opsForValue().set(mailAdress, code, 5, TimeUnit.MINUTES);
            //设置有效时间
            return R.ok();
        } else {
            return R.error().message("邮件发送失败");
        }

    }
}
