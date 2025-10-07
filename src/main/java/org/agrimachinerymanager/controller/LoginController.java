package org.agrimachinerymanager.controller;

import lombok.extern.slf4j.Slf4j;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.dto.LoginDTO;
import org.agrimachinerymanager.entity.SysUser;
import org.agrimachinerymanager.vo.LoginVo;
import org.agrimachinerymanager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 登录控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class LoginController {
    
    @Autowired
    private SysUserService sysUserService;
    
    /**
     * 用户登录接口
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public ApiResponse<LoginVo> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVo loginVo = sysUserService.login(loginDTO);
        log.info("当前登录信息：{}",loginVo);
        return ApiResponse.success(loginVo);
    }
    
    /**
     * 用户登出接口
     * @return 登出结果
     */
    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        // 在JWT认证机制中，登出主要是客户端删除令牌
        // 服务端可以通过黑名单机制增强安全性，但这里为了简化不做实现
        SecurityContextHolder.clearContext();
        return ApiResponse.success("退出登录成功");
    }
    
    /**
     * 获取当前登录用户信息
     * @return 当前登录用户信息
     */
    @GetMapping("/current-user")
    public ApiResponse<SysUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            // 注意：这里应该从数据库或缓存中获取用户信息，而不是直接使用Spring Security的用户信息
            // 因为Spring Security的用户信息可能不包含完整的用户数据
            // 为了简化，这里只返回用户名
            SysUser sysUser = new SysUser();
            sysUser.setUsername(username);
            return ApiResponse.success(sysUser);
        }
        return ApiResponse.fail("用户未登录");
    }
}