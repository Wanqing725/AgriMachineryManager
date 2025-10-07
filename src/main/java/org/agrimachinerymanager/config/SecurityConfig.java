package org.agrimachinerymanager.config;

import org.agrimachinerymanager.common.util.JwtUtil;
import org.agrimachinerymanager.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 * 配置接口的安全访问规则
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 配置BCrypt密码编码器
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置安全过滤链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 关闭CSRF保护（对于API项目通常不需要）
            .csrf(csrf -> csrf.disable())
            // 配置会话管理为无状态
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 配置请求授权规则
            .authorizeHttpRequests(auth -> auth
                // 允许访问登录接口
                .requestMatchers("/api/auth/login").permitAll()
                // 允许访问Knife4j/Swagger相关接口
                .requestMatchers("/doc.html", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                // 允许访问静态资源
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**", "/public/**").permitAll()
                // 允许访问公开API
                .requestMatchers("/api/public/**").permitAll()
                // 其他所有请求都需要认证
                .anyRequest().authenticated()
            )
            // 禁用表单登录（API项目通常使用JWT或其他认证方式）
            .formLogin(form -> form.disable())
            // 禁用HTTP基本认证
            .httpBasic(httpBasic -> httpBasic.disable())
            // 添加JWT认证过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}