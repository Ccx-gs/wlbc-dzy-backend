package com.shopping.config;

import com.shopping.common.UserHolder;
import com.shopping.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;
    private static final String TOKEN_PREFIX = "login:token:";
    private static final long TOKEN_TTL = 30;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        Map<Object, Object> userMap = stringRedisTemplate.opsForHash()
                .entries(TOKEN_PREFIX + token);

        if (userMap.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(Long.valueOf(userMap.get("id").toString()));
        userDTO.setNickname((String) userMap.get("nickname"));
        userDTO.setPhone((String) userMap.get("phone"));
        String avatar = (String) userMap.get("avatar");
        userDTO.setAvatar(avatar != null && !avatar.isEmpty() ? avatar : null);

        UserHolder.saveUser(userDTO);

        // 自动续期
        stringRedisTemplate.expire(TOKEN_PREFIX + token, TOKEN_TTL, TimeUnit.MINUTES);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        UserHolder.removeUser();
    }
}
