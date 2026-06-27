package com.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.dto.LoginFormDTO;
import com.shopping.dto.UserDTO;
import com.shopping.entity.User;

import com.shopping.dto.RegisterDTO;
public interface UserService extends IService<User> {
    String login(LoginFormDTO loginForm);
    void sendCode(String phone);
    void register(RegisterDTO registerDTO);
}
