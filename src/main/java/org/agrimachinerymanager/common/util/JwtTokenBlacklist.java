package org.agrimachinerymanager.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * JWT令牌黑名单管理类
 * 用于存储已登出的令牌，防止被重复使用
 * 使用Redis存储，支持分布式系统
 */
@Component
public class JwtTokenBlacklist {

    // Redis中的黑名单键前缀
    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 将令牌加入黑名单
     * @param token 要加入黑名单的令牌
     * @param expirationTime 令牌过期时间（毫秒）
     */
    public void addToBlacklist(String token, long expirationTime) {
        // 计算令牌在Redis中需要存储的时间（秒）
        long ttlInSeconds = (expirationTime - System.currentTimeMillis()) / 1000;
        
        // 确保TTL为正数
        if (ttlInSeconds > 0) {
            // 将令牌存储到Redis中，并设置过期时间
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, expirationTime, ttlInSeconds, TimeUnit.SECONDS);
        } else {
            // 如果令牌已经过期，不需要存储到黑名单
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, expirationTime, 1, TimeUnit.MINUTES);
        }
    }

    /**
     * 检查令牌是否在黑名单中
     * @param token 要检查的令牌
     * @return 如果令牌在黑名单中返回true，否则返回false
     */
    public boolean isBlacklisted(String token) {
        // 检查令牌是否在Redis黑名单中
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }

    /**
     * 从黑名单中移除令牌
     * @param token 要移除的令牌
     */
    public void removeFromBlacklist(String token) {
        redisTemplate.delete(BLACKLIST_PREFIX + token);
    }

    /**
     * 获取黑名单大小（已弃用）
     * 注意：此方法已废弃，因为keys命令在生产环境中会严重影响Redis性能
     * 建议使用独立的计数器来统计黑名单数量
     * @return 0 (该方法已废弃)
     * @deprecated 使用keys命令会阻塞Redis，请勿在生产环境使用
     */
    @Deprecated
    public int getBlacklistSize() {
        // 直接返回0，避免使用keys命令
        // 如果需要统计，建议使用Redis的INCR/DECR命令维护一个单独的计数器
        return 0;
    }
}