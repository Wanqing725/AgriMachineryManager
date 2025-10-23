package org.agrimachinerymanager.common.util;

import org.agrimachinerymanager.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis登录信息管理类
 * 用于在Redis中存储和管理用户的登录信息
 */
@Component
public class RedisLoginManager {

    // Redis中的用户登录信息键前缀
    private static final String USER_LOGIN_PREFIX = "jwt:user:login:";
    
    // 用户会话过期时间（秒），默认与JWT令牌过期时间一致
    private final long sessionExpiration; // 单位：秒

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 构造函数，初始化会话过期时间
     * 从JwtUtil中获取过期时间配置
     */
    public RedisLoginManager(@Autowired JwtUtil jwtUtil) {
        this.sessionExpiration = jwtUtil.getExpiration();
    }

    /**
     * 保存用户登录信息到Redis
     * @param userId 用户ID
     * @param username 用户名
     * @param token JWT令牌
     */
    public void saveLoginInfo(Long userId, String username, String token) {
        // 构建Redis键
        String userKey = USER_LOGIN_PREFIX + userId;
        
        // 创建用户登录信息对象
        UserLoginInfo loginInfo = new UserLoginInfo(userId, username, token, System.currentTimeMillis());
        
        // 保存到Redis，并设置过期时间
        redisTemplate.opsForValue().set(userKey, loginInfo, sessionExpiration, TimeUnit.SECONDS);
    }

    /**
     * 从Redis获取用户登录信息
     * @param userId 用户ID
     * @return 用户登录信息，如果不存在则返回null
     */
    public UserLoginInfo getLoginInfo(Long userId) {
        String userKey = USER_LOGIN_PREFIX + userId;
        Object value = redisTemplate.opsForValue().get(userKey);
        if (value instanceof UserLoginInfo) {
            // 重置过期时间，实现会话活跃续期
            redisTemplate.expire(userKey, sessionExpiration, TimeUnit.SECONDS);
            return (UserLoginInfo) value;
        }
        return null;
    }

    /**
     * 删除用户登录信息
     * @param userId 用户ID
     */
    public void removeLoginInfo(Long userId) {
        String userKey = USER_LOGIN_PREFIX + userId;
        redisTemplate.delete(userKey);
    }

    /**
     * 检查用户是否已登录
     * @param userId 用户ID
     * @return 如果用户已登录返回true，否则返回false
     */
    public boolean isLoggedIn(Long userId) {
        return redisTemplate.hasKey(USER_LOGIN_PREFIX + userId);
    }

    /**
     * 内部类：用户登录信息
     */
    public static class UserLoginInfo {
        private Long userId;
        private String username;
        private String token;
        private long loginTime; // 登录时间戳

        public UserLoginInfo(Long userId, String username, String token, long loginTime) {
            this.userId = userId;
            this.username = username;
            this.token = token;
            this.loginTime = loginTime;
        }

        // Getters and Setters
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(long loginTime) {
            this.loginTime = loginTime;
        }
    }
}