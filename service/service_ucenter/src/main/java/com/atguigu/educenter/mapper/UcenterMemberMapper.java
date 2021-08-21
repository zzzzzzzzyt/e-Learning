package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zzyt
 * @since 2021-08-14
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    int countRegister(String day);
}
