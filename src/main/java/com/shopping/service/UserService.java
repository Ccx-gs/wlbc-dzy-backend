package com.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.dto.LoginFormDTO;
import com.shopping.entity.User;

import com.shopping.dto.RegisterDTO;
import com.shopping.dto.UpdatePasswordDTO;
import com.shopping.dto.UpdateProfileDTO;
import com.shopping.dto.AddressDTO;
import com.shopping.dto.UserDTO;

import java.util.List;

public interface UserService extends IService<User> {
    String login(LoginFormDTO loginForm);
    void sendCode(String phone);
    void register(RegisterDTO registerDTO);

    UserDTO getCurrentUser();
    void updateProfile(UpdateProfileDTO dto);
    void updatePassword(UpdatePasswordDTO dto);
    void setPassword(String password);
    String uploadAvatar(org.springframework.web.multipart.MultipartFile file);

    List<AddressDTO> listAddresses();
    void addAddress(AddressDTO dto);
    void updateAddress(AddressDTO dto);
    void deleteAddress(Long id);
    void setDefaultAddress(Long id);
}
