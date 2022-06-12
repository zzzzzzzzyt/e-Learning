package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-14
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UcenterMemberMapper memberMapper;

    //登录操作
    @Override
    public String login(UcenterMember ucenterMember) {
        String email = ucenterMember.getEmail();
        String password = ucenterMember.getPassword();

        //邮箱号和密码非空判断
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }

        //判断邮箱号是否正确
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        UcenterMember member = this.baseMapper.selectOne(queryWrapper);

        //判断判断是否有该账号
        if (member == null) {
            throw new GuliException(20001, "登录失败");
        }

        //判断密码是否正确  但是注意 数据库中的密码 不会明面上展示 会采取md5的方式进行加密
        if (!Objects.equals(MD5.encrypt(password), member.getPassword())) {
            throw new GuliException(20001, "登录失败");
        }

        if (Boolean.TRUE.equals(member.getIsDisabled())) {
            throw new GuliException(20001, "登录失败");
        }

        //如果都不是的话 那代表注册成功 接下来要我们已 token传递 然后我们选择用jwt规则

        return JwtUtils.getJwtToken(member.getId(), member.getNickname());

    }


    //进行注册
    @Override
    public void register(RegisterVo registerVo) {
        String email = registerVo.getEmail();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();

        //判断字段是否存在 不存在的话 就直接抛出异常
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "账号注册失败");
        }

        //判断验证码是否正确或已经过期
        if (!code.equals(redisTemplate.opsForValue().get(email))) {
            throw new GuliException(20001, "验证码错误或已失效");
        }

        //判断是否存在相同邮箱号
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        if (baseMapper.selectCount(queryWrapper) > 0) {
            throw new GuliException(20001, "该邮箱已被注册");
        }

        //如果都没事的话 那就写入数据库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setEmail(email);
        ucenterMember.setPassword(MD5.encrypt(password));  //千万记得 密码是要加密后存入
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false); //不禁用
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132"); //设置默认头像
        baseMapper.insert(ucenterMember);
    }


    //根据openid进行查找
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        return ucenterMember;
    }

    //根据日期查询某天的注册人数
    @Override
    public int countRegister(String day) {
        return memberMapper.countRegister(day);
    }
}
