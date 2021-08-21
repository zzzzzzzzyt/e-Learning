package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.handler.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
//@RequestMapping("/api/ucenter/wx")
public class WxApiController
{

    @Autowired
    private UcenterMemberService memberService;

    //获取扫码人信息 添加数据
    @GetMapping("callback")
    public String callback(String code,String state)
    {
        //1 获取code值，临时票据，类似于验证码
        //向认证服务器发送请求交换access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,ConstantWxUtils.WX_OPEN_APP_ID,ConstantWxUtils.WX_OPEN_APP_SECRET,code);
        String result = null;
        try
        {
            result = HttpClientUtils.get(accessTokenUrl);

            //解析json字符串
            Gson gson = new Gson();
            HashMap map = gson.fromJson(result, HashMap.class);

            //2 拿着code请求微信固定的地址，得到两个值 access_token和openid
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            //用户的信息是肯定能获取到的但是不知道是不是存在用户
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member==null)  //如果用户不存在 再进行接下来的步骤 不然不用浪费时间了额
            {
                //3 拿着accesstoken 和 openid 再去请求微信提供的固定地址 获得扫码人的信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                String userInfo = HttpClientUtils.get(userInfoUrl);

                //根据 获取的用户信息 查找数据库中是否已经存在数据库信息 不存在则添加
                //先转换数据
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname"); //昵称
                System.out.println("+++++++++++++++++++++++++++++++"+nickname);//打印一下昵称
                String headimgurl = (String) userInfoMap.get("headimgurl");  //头像
                member = new UcenterMember();
                member.setAvatar(headimgurl);
                member.setNickname(nickname);
                member.setOpenid(openid);

                //将数据保存进 数据库
                memberService.save(member);
            }
            //用token传递信息
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token="+jwtToken;

        }catch (Exception e)
        {
            throw new GuliException(20001,e.getMessage());
        }




    }

    @GetMapping("login")
    public String getWxCode()
    {
        String codeUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s"+
                "&redirect_uri=%s"+
                "&response_type=code"+
                "&scope=snsapi_login"+
                "&state=%s"+
                "#wechat_redirect";
        //回调地址 要编码
        String redirectUri = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try
        {
            redirectUri = URLEncoder.encode(redirectUri,"UTF-8");
        }catch (Exception e){
            throw new GuliException(20001,e.getMessage());
        }

        //设置%s里面的值
        String url = String.format(
                codeUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUri,
                "atguigu"
        );

        return "redirect:"+url;
    }
}
