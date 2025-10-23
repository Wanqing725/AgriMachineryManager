package org.agrimachinerymanager.controller;

import lombok.extern.slf4j.Slf4j;
import org.agrimachinerymanager.common.util.JwtTokenBlacklist;
import org.agrimachinerymanager.common.util.JwtUtil;
import org.agrimachinerymanager.common.util.RedisLoginManager;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.dto.LoginDTO;
import org.agrimachinerymanager.entity.SysUser;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.vo.LoginVo;
import org.agrimachinerymanager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Date;

/**
 * 登录控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {
    
    @Autowired
    private SysUserService sysUserService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private JwtTokenBlacklist jwtTokenBlacklist;
    
    @Autowired
    private RedisLoginManager redisLoginManager;
    
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
     * 将令牌加入黑名单，确保无法再被使用
     * @return 登出结果
     */
    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest request) {
        // 获取请求头中的Authorization
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                // 获取令牌的过期时间
                Date expirationDate = jwtUtil.getExpirationDateFromToken(token);
                // 将令牌加入黑名单，设置黑名单保留时间为令牌的过期时间
                jwtTokenBlacklist.addToBlacklist(token, expirationDate.getTime());
                log.info("用户登出成功，令牌已加入黑名单");
            } catch (Exception e) {
                log.error("处理令牌黑名单时发生错误: {}", e.getMessage());
                // 即使处理令牌失败，也清除上下文，不影响用户登出
            }
            
            try {
                // 从令牌中获取用户ID
                Long userId = jwtUtil.getUserIdFromToken(token);
                // 从Redis中删除用户登录信息
                redisLoginManager.removeLoginInfo(userId);
                log.info("用户 [{}] 的登录信息已从Redis中删除", userId);
            } catch (Exception e) {
                log.error("从Redis中删除用户登录信息时发生错误: {}", e.getMessage());
                // 即使处理失败，也不影响用户登出
            }
        }
        
        // 清除Security上下文
        SecurityContextHolder.clearContext();
        return ApiResponse.success("退出登录成功");
    }
    
    /**
     * 获取当前登录用户信息
     * @return 当前登录用户信息
     */
    @GetMapping("/currentUser")
    public ApiResponse<SysUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            String username = authentication.getName();
            try {
                // 从数据库获取完整的用户信息
                // 密码字段已通过@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)自动脱敏
                SysUser sysUser = sysUserService.getSysUserByUsername(username);
                return ApiResponse.success(sysUser);
            } catch (BaseException e) {
                log.error("获取当前用户信息失败: {}", e.getMessage());
                return ApiResponse.fail(e.getMessage());
            }
        }
        return ApiResponse.fail("用户未登录");
    }
}