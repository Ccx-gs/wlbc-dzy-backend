package com.shopping.controller;

import com.shopping.common.Result;
import com.shopping.dto.AddressDTO;
import com.shopping.dto.SetPasswordDTO;
import com.shopping.dto.UpdatePasswordDTO;
import com.shopping.dto.UpdateProfileDTO;
import com.shopping.dto.UserDTO;
import com.shopping.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public Result<UserDTO> getCurrentUser() {
        return Result.ok(userService.getCurrentUser());
    }

    @PutMapping("/profile")
    public Result<Void> updateProfile(@Valid @RequestBody UpdateProfileDTO dto) {
        userService.updateProfile(dto);
        return Result.ok();
    }

    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        String avatarUrl = userService.uploadAvatar(file);
        return Result.ok(avatarUrl);
    }

    @PostMapping("/password/set")
    public Result<Void> setPassword(@Valid @RequestBody SetPasswordDTO dto) {
        userService.setPassword(dto.getNewPassword());
        return Result.ok();
    }

    @PostMapping("/password/update")
    public Result<Void> updatePassword(@Valid @RequestBody UpdatePasswordDTO dto) {
        userService.updatePassword(dto);
        return Result.ok();
    }

    @GetMapping("/address/list")
    public Result<List<AddressDTO>> listAddresses() {
        return Result.ok(userService.listAddresses());
    }

    @PostMapping("/address")
    public Result<Void> addAddress(@Valid @RequestBody AddressDTO dto) {
        userService.addAddress(dto);
        return Result.ok();
    }

    @PutMapping("/address")
    public Result<Void> updateAddress(@Valid @RequestBody AddressDTO dto) {
        userService.updateAddress(dto);
        return Result.ok();
    }

    @DeleteMapping("/address/{id}")
    public Result<Void> deleteAddress(@PathVariable Long id) {
        userService.deleteAddress(id);
        return Result.ok();
    }

    @PutMapping("/address/default/{id}")
    public Result<Void> setDefaultAddress(@PathVariable Long id) {
        userService.setDefaultAddress(id);
        return Result.ok();
    }
}
