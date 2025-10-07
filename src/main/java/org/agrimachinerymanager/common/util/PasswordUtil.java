package org.agrimachinerymanager.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 * 提供密码加密和验证功能
 */
@Component
public class PasswordUtil {

    private static BCryptPasswordEncoder passwordEncoder;

    /**
     * 注入Spring Security配置的BCryptPasswordEncoder实例
     */
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder encoder) {
        PasswordUtil.passwordEncoder = encoder;
    }

    /**
     * 对密码进行BCrypt加密
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证密码是否匹配
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}