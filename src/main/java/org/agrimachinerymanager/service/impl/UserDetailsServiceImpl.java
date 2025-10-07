package org.agrimachinerymanager.service.impl;

import org.agrimachinerymanager.entity.SysUser;
import org.agrimachinerymanager.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 自定义UserDetailsService实现，用于Spring Security从数据库加载用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("📝 尝试从数据库加载用户: {}", username);
        
        // 从数据库查询用户信息
        SysUser sysUser = sysUserMapper.selectOne( 
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<SysUser>()
                .eq("username", username)
                .eq("status", 1)  // 只查询状态为启用的用户
        );
        
        if (sysUser == null) {
            log.error("❌ 未找到用户: {}", username);
            throw new UsernameNotFoundException("用户不存在或用户名错误");
        }
        
        log.info("✅ 成功加载用户: {}，用户ID: {}", username, sysUser.getId());
        
        // 构建用户权限列表
        // 这里简化处理，实际项目中应该从数据库查询用户的角色和权限
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + sysUser.getRole())
        );
        
        // 返回Spring Security的User对象
        return new User(
            sysUser.getUsername(),
            sysUser.getPassword(),
            true,  // 账户是否启用
            true,  // 账户是否过期
            true,  // 凭证是否过期
            true,  // 账户是否锁定
            authorities  // 权限列表
        );
    }
}