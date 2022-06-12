package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonvo.UcenterMemberVo;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zzyt
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Resource
    private UcenterMemberService memberService;


    //进行登录操作
    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember) {
        //通过登录传过来的对象只封装了用户名和密码
        String token = memberService.login(ucenterMember);

        return R.ok().data("token", token);
    }

    //进行注册操作
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获得用户信息 用于前端页面的显示  调用jwt中的方法
    @GetMapping("getMemberInfo")
    public R getUserInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据获取得到的成员id获取成员信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("UserInfo", member).data("avatar", member.getAvatar()).data("nickName", member.getNickname());
    }

    @GetMapping("getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMember memeber = new UcenterMember();
        BeanUtils.copyProperties(ucenterMember, memeber);
        return memeber;
    }


    //根据用户id获取用户信息  返回给order 进行远程调用
    @GetMapping("getUserInfoOrder/{id}")
    public UcenterMemberVo getMemberInfoOrder(@PathVariable("id") String id) {
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(ucenterMember, ucenterMemberVo);
        return ucenterMemberVo;
    }

    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day) {
        int count = memberService.countRegister(day);
        return R.ok().data("count", count);
    }

}

