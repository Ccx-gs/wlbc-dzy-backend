package com.shopping.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.common.BusinessException;
import com.shopping.dto.LoginFormDTO;
import com.shopping.dto.RegisterDTO;
import com.shopping.dto.UserDTO;
import com.shopping.entity.User;
import com.shopping.mapper.UserMapper;
import com.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;
    private static final String CODE_PREFIX = "login:code:";
    private static final String TOKEN_PREFIX = "login:token:";
    private static final long CODE_TTL = 5;
    private static final long TOKEN_TTL = 30;

    @Override
    public void sendCode(String phone) {
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(CODE_PREFIX + phone, code, CODE_TTL, TimeUnit.MINUTES);
        log.info("code: {} -> {}", phone, code);
    }

    @Override
    public void register(RegisterDTO dto) {
        String phone = dto.getPhone();
        String cachedCode = stringRedisTemplate.opsForValue().get(CODE_PREFIX + phone);
        if (cachedCode == null || !cachedCode.equals(dto.getCode())) {
            throw new BusinessException(400, "?????????");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException(400, "???????");
        }
        if (lambdaQuery().eq(User::getPhone, phone).one() != null) {
            throw new BusinessException(400, "?????????????");
        }
        User user = new User();
        user.setUsername(phone);
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : "??" + RandomUtil.randomNumbers(6));
        user.setStatus(1);
        save(user);
        stringRedisTemplate.delete(CODE_PREFIX + phone);
    }

    @Override
    public String login(LoginFormDTO loginForm) {
        String phone = loginForm.getPhone();
        String code = loginForm.getCode();
        String cachedCode = stringRedisTemplate.opsForValue().get(CODE_PREFIX + phone);
        if (cachedCode == null || !cachedCode.equals(code)) {
            throw new BusinessException(400, "?????????");
        }
        stringRedisTemplate.delete(CODE_PREFIX + phone);
        User user = lambdaQuery().eq(User::getPhone, phone).one();
        if (user == null) {
            throw new BusinessException(400, "????????????");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException(403, "??????");
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNickname(user.getNickname());
        userDTO.setPhone(user.getPhone());
        userDTO.setAvatar(user.getAvatar());
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(userDTO.getId()));
        userMap.put("nickname", userDTO.getNickname());
        userMap.put("phone", userDTO.getPhone());
        userMap.put("avatar", userDTO.getAvatar() != null ? userDTO.getAvatar() : "");
        stringRedisTemplate.opsForHash().putAll(TOKEN_PREFIX + token, userMap);
        stringRedisTemplate.expire(TOKEN_PREFIX + token, TOKEN_TTL, TimeUnit.MINUTES);
        return token;
    }
}
