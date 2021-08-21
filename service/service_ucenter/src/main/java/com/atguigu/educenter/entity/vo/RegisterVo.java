package com.atguigu.educenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterVo
{
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
