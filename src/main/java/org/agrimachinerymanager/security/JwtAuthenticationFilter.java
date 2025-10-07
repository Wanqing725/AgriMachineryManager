package org.agrimachinerymanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.agrimachinerymanager.common.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT认证过滤器（完整版带日志）
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    // 不需要认证的路径列表，考虑前缀/agri-machinery
    private static final List<String> PERMIT_ALL_PATHS = Arrays.asList(
            "/agri-machinery/api/auth/login",
            "/api/auth/login",
            "/agri-machinery/doc.html",
            "/doc.html",
            "/agri-machinery/swagger-ui.html",
            "/swagger-ui.html",
            "/agri-machinery/swagger-ui/**",
            "/swagger-ui/**",
            "/agri-machinery/v3/api-docs/**",
            "/v3/api-docs/**",
            "/agri-machinery/swagger-resources/**",
            "/swagger-resources/**",
            "/agri-machinery/webjars/**",
            "/webjars/**",
            "/agri-machinery/css/**",
            "/css/**",
            "/agri-machinery/js/**",
            "/js/**",
            "/agri-machinery/images/**",
            "/images/**",
            "/agri-machinery/static/**",
            "/static/**",
            "/agri-machinery/public/**",
            "/public/**",
            "/agri-machinery/api/public/**",
            "/api/public/**"
    );

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.info("➡️ 请求路径: {}", requestURI);
        
        // 检查是否是不需要认证的路径
        if (isPermitAllPath(requestURI)) {
            log.info("✅ 该路径无需认证，直接放行");
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        log.info("➡️ Authorization头: {}", header);

        String username = null;
        String jwt = null;

        // 提取 Bearer token
        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
            log.info("✅ 提取到JWT: {}", jwt);

            try {
                username = jwtUtil.getUsernameFromToken(jwt);
                log.info("✅ 解析出的用户名: {}", username);
            } catch (Exception e) {
                log.error("❌ JWT解析失败: {}", e.getMessage());
            }
        } else if (header != null) {
            log.warn("⚠️ Authorization头格式不正确，应以 'Bearer ' 开头");
        } else {
            log.warn("⚠️ 请求未携带Authorization头");
        }

        // 校验token并注入SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("🔍 从UserDetailsService加载用户: {}", username);
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("✅ JWT验证成功，用户 [{}] 已通过认证", username);
                } else {
                    log.warn("❌ JWT验证失败，用户名: {}", username);
                }
            } catch (UsernameNotFoundException e) {
                log.error("❌ 用户不存在: {}", username);
                // 继续执行过滤器链，让全局异常处理器处理这个异常
            } catch (Exception e) {
                log.error("❌ 用户认证过程中发生错误: {}", e.getMessage());
            }
        } else if (username == null) {
            log.warn("⚠️ 未能从JWT中解析出用户名");
        } else {
            log.debug("🟡 用户 [{}] 已经在SecurityContext中，无需重复认证", username);
        }

        chain.doFilter(request, response);
    }
    
    /**
     * 检查请求路径是否是不需要认证的路径
     */
    private boolean isPermitAllPath(String requestURI) {
        for (String path : PERMIT_ALL_PATHS) {
            if (pathMatcher.match(path, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
