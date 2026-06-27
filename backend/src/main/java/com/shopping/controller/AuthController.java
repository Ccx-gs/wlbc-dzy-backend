package com.shopping.controller;

import com.shopping.common.Result;
import com.shopping.dto.LoginFormDTO;
import com.shopping.dto.RegisterDTO;
import com.shopping.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/send-code")
    public Result<Void> sendCode(@RequestParam String phone) {
        userService.sendCode(phone);
        return Result.ok();
    }

    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginFormDTO loginForm) {
        String token = userService.login(loginForm);
        return Result.ok(token);
    }

    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return Result.ok();
    }
}
