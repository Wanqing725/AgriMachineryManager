package org.agrimachinerymanager.vo;

import lombok.Data;

/**
 * 登录响应Vo类
 */
@Data
public class LoginVo {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 角色
     */
    private Integer role;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * JWT令牌
     */
    private String token;
    
    /**
     * 令牌类型
     */
    private String tokenType = "Bearer";
}