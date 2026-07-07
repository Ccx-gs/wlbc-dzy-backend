package com.shopping.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.common.BusinessException;
import com.shopping.common.UserHolder;
import com.shopping.dto.AddressDTO;
import com.shopping.dto.LoginFormDTO;
import com.shopping.dto.RegisterDTO;
import com.shopping.dto.UpdatePasswordDTO;
import com.shopping.dto.UpdateProfileDTO;
import com.shopping.dto.UserDTO;
import com.shopping.entity.Address;
import com.shopping.entity.User;
import com.shopping.mapper.AddressMapper;
import com.shopping.mapper.UserMapper;
import com.shopping.service.UserService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AddressMapper addressMapper;
    private static final String CODE_PREFIX = "login:code:";
    private static final String TOKEN_PREFIX = "login:token:";
    private static final long CODE_TTL = 5;
    private static final long TOKEN_TTL = 30;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Value("${file.access-url:http://localhost:8080/uploads}")
    private String accessUrl;

    @Override
    public void sendCode(String phone) {
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(CODE_PREFIX + phone, code, CODE_TTL, TimeUnit.MINUTES);
        String banner = "\n\n" +
            "╔══════════════════════════════════════════╗\n" +
            "║                                          ║\n" +
            "║   📱 短信验证码（开发模式）              ║\n" +
            "║                                          ║\n" +
            "║   手机号: " + padRight(phone, 28) + "║\n" +
            "║   验证码: " + padRight(code, 28) + "║\n" +
            "║   有效期: " + padRight(CODE_TTL + " 分钟", 28) + "║\n" +
            "║                                          ║\n" +
            "╚══════════════════════════════════════════╝\n";
        System.out.println(banner);
        log.info("发送验证码: {} -> {}", phone, code);
    }

    private String padRight(String s, int width) {
        if (s == null) s = "";
        int chineseCount = 0;
        for (char c : s.toCharArray()) {
            if (c > 127) chineseCount++;
        }
        int pad = width - s.length() - chineseCount;
        if (pad <= 0) return s;
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < pad; i++) sb.append(' ');
        return sb.toString();
    }

    @Override
    public void register(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException(400, "两次密码输入不一致");
        }
        
        String password = dto.getPassword();
        if (password.length() < 6 || password.length() > 20) {
            throw new BusinessException(400, "密码长度需在6-20位之间");
        }
        boolean hasDigit = false;
        boolean hasLetter = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }
        if (!hasDigit || !hasLetter) {
            throw new BusinessException(400, "密码必须同时包含数字和英文字母");
        }
        
        String phone = dto.getPhone();
        String code = dto.getCode();
        
        String cachedCode = stringRedisTemplate.opsForValue().get(CODE_PREFIX + phone);
        if (cachedCode == null) {
            throw new BusinessException(400, "验证码不存在或已过期，请重新获取");
        }
        if (!cachedCode.equals(code)) {
            throw new BusinessException(400, "验证码错误");
        }
        if (lambdaQuery().eq(User::getPhone, phone).one() != null) {
            throw new BusinessException(400, "该手机号已注册");
        }
        User user = new User();
        user.setUsername(phone);
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : "用户" + RandomUtil.randomNumbers(6));
        user.setStatus(1);
        save(user);
        stringRedisTemplate.delete(CODE_PREFIX + phone);
    }
    
    private String createToken(User user) {
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

    @Override
    public String login(LoginFormDTO loginForm) {
        String phone = loginForm.getPhone();
        String loginType = loginForm.getLoginType();
        
        User user = lambdaQuery().eq(User::getPhone, phone).one();
        
        if ("password".equals(loginType)) {
            // 密码登录模式
            String password = loginForm.getPassword();
            if (password == null || password.isEmpty()) {
                throw new BusinessException(400, "密码不能为空");
            }
            if (user == null) {
                throw new BusinessException(400, "手机号或密码错误");
            }
            if (user.getPassword() == null) {
                throw new BusinessException(400, "该账号未设置密码，请先通过验证码登录后设置密码");
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BusinessException(400, "手机号或密码错误");
            }
            log.info("密码登录成功: phone={}", phone);
        } else {
            // 验证码登录模式
            String code = loginForm.getCode();
            String cachedCode = stringRedisTemplate.opsForValue().get(CODE_PREFIX + phone);
            if (cachedCode == null || !cachedCode.equals(code)) {
                throw new BusinessException(400, "验证码错误");
            }
            stringRedisTemplate.delete(CODE_PREFIX + phone);
            
            // 用户不存在，自动注册（黑马点评模式）
            if (user == null) {
                user = new User();
                user.setUsername(phone);
                user.setPhone(phone);
                user.setNickname("用户" + RandomUtil.randomNumbers(6));
                user.setStatus(1);
                save(user);
                log.info("自动注册新用户: {}", phone);
            }
        }
        
        if (user.getStatus() == 0) {
            throw new BusinessException(403, "账号已被禁用");
        }
        
        return createToken(user);
    }

    private void refreshRedisUserCache(Long userId) {
        User user = getById(userId);
        if (user == null) return;
        String token = UserHolder.getToken();
        if (token == null) return;
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(user.getId()));
        userMap.put("nickname", user.getNickname() != null ? user.getNickname() : "");
        userMap.put("phone", user.getPhone() != null ? user.getPhone() : "");
        userMap.put("avatar", user.getAvatar() != null ? user.getAvatar() : "");
        stringRedisTemplate.opsForHash().putAll(TOKEN_PREFIX + token, userMap);
        stringRedisTemplate.expire(TOKEN_PREFIX + token, TOKEN_TTL, TimeUnit.MINUTES);
    }

    @Override
    public UserDTO getCurrentUser() {
        UserDTO holder = UserHolder.getUser();
        User user = getById(holder.getId());
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        holder.setNickname(user.getNickname());
        holder.setAvatar(user.getAvatar());
        return holder;
    }

    @Override
    public void updateProfile(UpdateProfileDTO dto) {
        Long userId = UserHolder.getUser().getId();
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        user.setNickname(dto.getNickname());
        updateById(user);
        refreshRedisUserCache(userId);
    }

    @Override
    public void updatePassword(UpdatePasswordDTO dto) {
        Long userId = UserHolder.getUser().getId();
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        
        if (user.getPassword() == null) {
            throw new BusinessException(400, "该账号未设置密码，请使用设置密码功能");
        }
        
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException(400, "旧密码错误");
        }
        
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException(400, "两次密码输入不一致");
        }
        
        String password = dto.getNewPassword();
        if (password.length() < 6 || password.length() > 20) {
            throw new BusinessException(400, "密码长度需在6-20位之间");
        }
        boolean hasDigit = false;
        boolean hasLetter = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }
        if (!hasDigit || !hasLetter) {
            throw new BusinessException(400, "密码必须同时包含数字和英文字母");
        }
        
        user.setPassword(passwordEncoder.encode(password));
        updateById(user);
        log.info("密码修改成功: userId={}", userId);
    }

    @Override
    public void setPassword(String password) {
        Long userId = UserHolder.getUser().getId();
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        
        if (user.getPassword() != null) {
            throw new BusinessException(400, "该账号已设置密码，请使用修改密码功能");
        }
        
        if (password.length() < 6 || password.length() > 20) {
            throw new BusinessException(400, "密码长度需在6-20位之间");
        }
        boolean hasDigit = false;
        boolean hasLetter = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }
        if (!hasDigit || !hasLetter) {
            throw new BusinessException(400, "密码必须同时包含数字和英文字母");
        }
        
        user.setPassword(passwordEncoder.encode(password));
        updateById(user);
        log.info("密码设置成功: userId={}", userId);
    }

    @Override
    public String uploadAvatar(org.springframework.web.multipart.MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(400, "文件不能为空");
        }
        Long userId = UserHolder.getUser().getId();
        String dirPath = System.getProperty("user.dir") + File.separator + uploadDir;
        FileUtil.mkdir(dirPath);
        String ext = FileUtil.extName(file.getOriginalFilename());
        if (ext == null || ext.isEmpty()) {
            ext = "jpg";
        }
        String fileName = "avatar_" + userId + "_" + System.currentTimeMillis() + "." + ext;
        File dest = new File(dirPath + File.separator + fileName);
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            throw new BusinessException(500, "文件保存失败: " + e.getMessage());
        }

        String avatarUrl = accessUrl + "/" + fileName;
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        user.setAvatar(avatarUrl);
        updateById(user);
        refreshRedisUserCache(userId);

        log.info("头像上传成功: userId={}, url={}", userId, avatarUrl);
        return avatarUrl;
    }

    @Override
    public List<AddressDTO> listAddresses() {
        Long userId = UserHolder.getUser().getId();
        List<Address> addresses = addressMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Address>()
                        .eq(Address::getUserId, userId)
                        .orderByDesc(Address::getIsDefault)
                        .orderByDesc(Address::getUpdateTime)
        );
        List<AddressDTO> result = new ArrayList<>();
        for (Address addr : addresses) {
            AddressDTO dto = new AddressDTO();
            dto.setId(addr.getId());
            dto.setReceiverName(addr.getReceiverName());
            dto.setReceiverPhone(addr.getReceiverPhone());
            dto.setProvince(addr.getProvince());
            dto.setCity(addr.getCity());
            dto.setDistrict(addr.getDistrict());
            dto.setDetailAddr(addr.getDetailAddr());
            dto.setLongitude(addr.getLongitude());
            dto.setLatitude(addr.getLatitude());
            dto.setIsDefault(addr.getIsDefault());
            result.add(dto);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAddress(AddressDTO dto) {
        Long userId = UserHolder.getUser().getId();
        Address address = new Address();
        address.setUserId(userId);
        address.setReceiverName(dto.getReceiverName());
        address.setReceiverPhone(dto.getReceiverPhone());
        address.setProvince(dto.getProvince());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setDetailAddr(dto.getDetailAddr());
        address.setLongitude(dto.getLongitude());
        address.setLatitude(dto.getLatitude());
        address.setIsDefault(dto.getIsDefault() != null ? dto.getIsDefault() : 0);
        if (address.getIsDefault() == 1) {
            clearDefaultAddresses(userId);
        }
        addressMapper.insert(address);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(AddressDTO dto) {
        Long userId = UserHolder.getUser().getId();
        Address address = addressMapper.selectById(dto.getId());
        if (address == null || !address.getUserId().equals(userId)) {
            throw new BusinessException(404, "地址不存在");
        }
        address.setReceiverName(dto.getReceiverName());
        address.setReceiverPhone(dto.getReceiverPhone());
        address.setProvince(dto.getProvince());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setDetailAddr(dto.getDetailAddr());
        address.setLongitude(dto.getLongitude());
        address.setLatitude(dto.getLatitude());
        if (dto.getIsDefault() != null && dto.getIsDefault() == 1) {
            clearDefaultAddresses(userId);
            address.setIsDefault(1);
        } else if (dto.getIsDefault() != null && dto.getIsDefault() == 0) {
            address.setIsDefault(0);
        }
        addressMapper.updateById(address);
    }

    @Override
    public void deleteAddress(Long id) {
        Long userId = UserHolder.getUser().getId();
        Address address = addressMapper.selectById(id);
        if (address == null || !address.getUserId().equals(userId)) {
            throw new BusinessException(404, "地址不存在");
        }
        addressMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefaultAddress(Long id) {
        Long userId = UserHolder.getUser().getId();
        Address address = addressMapper.selectById(id);
        if (address == null || !address.getUserId().equals(userId)) {
            throw new BusinessException(404, "地址不存在");
        }
        clearDefaultAddresses(userId);
        address.setIsDefault(1);
        addressMapper.updateById(address);
    }

    private void clearDefaultAddresses(Long userId) {
        addressMapper.update(null,
                new com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<Address>()
                        .eq(Address::getUserId, userId)
                        .set(Address::getIsDefault, 0)
        );
    }
}
