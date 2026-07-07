package com.shopping.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginFormDTO {
    @NotBlank(message = "手机号不能为空")
    private String phone;
    
    // 登录类型：code=验证码登录，password=密码登录
    private String loginType = "code";
    
    // 验证码登录
    private String code;
    
    // 密码登录
    private String password;
}
