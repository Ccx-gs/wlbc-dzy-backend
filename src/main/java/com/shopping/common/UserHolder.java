package com.shopping.common;

import com.shopping.dto.UserDTO;

public class UserHolder {
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();
    private static final ThreadLocal<String> tokenTl = new ThreadLocal<>();

    public static void saveUser(UserDTO user) {
        tl.set(user);
    }

    public static UserDTO getUser() {
        return tl.get();
    }

    public static void saveToken(String token) {
        tokenTl.set(token);
    }

    public static String getToken() {
        return tokenTl.get();
    }

    public static void removeUser() {
        tl.remove();
        tokenTl.remove();
    }
}
