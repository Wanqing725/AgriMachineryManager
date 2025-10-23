package org.agrimachinerymanager.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 系统用户DTO类
 * 用于接收和验证用户输入参数
 */
@Data
public class SysUserDTO {

    /**
     * 用户ID（更新时必填）
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;

    /**
     * 密码（新增时必填）
     */
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 50, message = "真实姓名不能超过50个字符")
    private String realName;

    /**
     * 联系电话
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 角色（1-管理员，2-操作员）
     */
    @NotNull(message = "角色不能为空")
    @Min(value = 1, message = "角色值必须为1或2")
    @Max(value = 2, message = "角色值必须为1或2")
    private Integer role;

    /**
     * 状态（0-禁用，1-正常）
     */
    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态值必须为0或1")
    @Max(value = 1, message = "状态值必须为0或1")
    private Integer status;
}

