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

import java.util.ArrayList;
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
        
        log.info("✅ 成功加载用户: {}，用户ID: {}, 角色: {}", username, sysUser.getId(), sysUser.getRole());
        
        // 构建用户权限列表 - 从数据库查询用户的角色和权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        // 根据用户表中的role字段构建角色权限
        Integer roleCode = sysUser.getRole();
        if (roleCode != null) {
            // 根据role字段的值（1-管理员，2-操作员）设置对应的角色名称
            String roleName;
            if (roleCode == 1) {
                roleName = "ADMIN";
            } else if (roleCode == 2) {
                roleName = "OPERATOR";
            } else {
                roleName = "USER";
            }
            
            // 添加角色权限，前缀"ROLE_"是Spring Security的约定
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
            log.debug("🔑 为用户 {} 添加角色权限: ROLE_{}", username, roleName);
            
            // 根据角色添加具体的操作权限
            addPermissionsByRole(authorities, roleCode, username);
        }
        
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
    
    /**
     * 根据角色添加具体的操作权限
     * @param authorities 权限列表
     * @param roleCode 角色代码
     * @param username 用户名
     */
    private void addPermissionsByRole(List<SimpleGrantedAuthority> authorities, Integer roleCode, String username) {
        // 管理员角色拥有所有权限
        if (roleCode == 1) {
            authorities.add(new SimpleGrantedAuthority("ALL_ACCESS"));
            authorities.add(new SimpleGrantedAuthority("MANAGE_USERS"));
            authorities.add(new SimpleGrantedAuthority("MANAGE_MACHINES"));
            authorities.add(new SimpleGrantedAuthority("VIEW_REPORTS"));
            log.debug("👑 为管理员用户 {} 添加所有操作权限", username);
        }
        // 操作员角色拥有基本操作权限
        else if (roleCode == 2) {
            authorities.add(new SimpleGrantedAuthority("OPERATE_MACHINES"));
            authorities.add(new SimpleGrantedAuthority("VIEW_MACHINES"));
            authorities.add(new SimpleGrantedAuthority("VIEW_REPORTS"));
            log.debug("🔧 为操作员用户 {} 添加操作权限", username);
        }
    }
}