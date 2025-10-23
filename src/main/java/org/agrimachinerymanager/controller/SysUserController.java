package org.agrimachinerymanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.entity.SysUser;
import org.agrimachinerymanager.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户控制器
 */
@RestController
@RequestMapping("/sys-user")
@Tag(name = "系统用户管理", description = "系统用户的增删改查操作")
public class SysUserController {
    private static final Logger log = LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取所有系统用户
     * @return 用户列表
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllSysUsers")
    @Operation(summary = "获取所有系统用户", description = "查询系统中所有的用户信息")
    public ApiResponse<List<SysUser>> getAllSysUsers() {
        log.info("获取所有系统用户");
        List<SysUser> users = sysUserService.getAllSysUsers();
        return ApiResponse.success(users);
    }

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getSysUserById/{id}")
    @Operation(summary = "根据ID获取用户信息", description = "根据用户ID查询用户的详细信息")
    public ApiResponse<SysUser> getSysUserById(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("根据ID获取用户信息: {}", id);
        SysUser sysUser = sysUserService.getSysUserById(id);
        return ApiResponse.success(sysUser);
    }

    /**
     * 新增用户
     * @param sysUser 用户信息
     * @return 操作结果
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addSysUser")
    @Operation(summary = "新增用户", description = "添加新的系统用户")
    public ApiResponse<SysUser> addSysUser(
            @Parameter(description = "用户信息", required = true)
            @RequestBody SysUser sysUser) {
        log.info("新增用户: {}", sysUser);
        sysUserService.addSysUser(sysUser);
        return ApiResponse.success(sysUser);
    }

    /**
     * 更新用户
     * @param sysUser 用户信息
     * @return 操作结果
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateSysUser")
    @Operation(summary = "更新用户", description = "更新已有的用户信息")
    public ApiResponse<Boolean> updateSysUser(
            @Parameter(description = "用户信息", required = true)
            @RequestBody SysUser sysUser) {
        log.info("更新用户: {}", sysUser);
        sysUserService.updateSysUser(sysUser);
        return ApiResponse.success(true);
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 操作结果
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteSysUser/{id}")
    @Operation(summary = "删除用户", description = "根据ID删除系统用户")
    public ApiResponse<Boolean> deleteSysUser(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("删除用户: {}", id);
        sysUserService.deleteSysUser(id);
        return ApiResponse.success(true);
    }

    /**
     * 分页查询用户
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param username 用户名（可选）
     * @param realName 真实姓名（可选）
     * @param phone 联系电话（可选）
     * @param role 角色（可选）
     * @param status 状态（可选）
     * @return 分页结果
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getSysUserPage")
    @Operation(summary = "分页查询用户", description = "分页查询系统用户列表，支持条件筛选")
    public ApiResponse<Page<SysUser>> getSysUserPage(
            @Parameter(description = "页码", required = true, example = "1")
            @RequestParam("pageNum") int pageNum,
            @Parameter(description = "每页条数", required = true, example = "10")
            @RequestParam("pageSize") int pageSize,
            @Parameter(description = "用户名")
            @RequestParam(value = "username", required = false) String username,
            @Parameter(description = "真实姓名")
            @RequestParam(value = "realName", required = false) String realName,
            @Parameter(description = "联系电话")
            @RequestParam(value = "phone", required = false) String phone,
            @Parameter(description = "角色")
            @RequestParam(value = "role", required = false) Integer role,
            @Parameter(description = "状态")
            @RequestParam(value = "status", required = false) Integer status) {
        log.info("分页查询用户: pageNum={}, pageSize={}, username={}, realName={}, phone={}, role={}, status={}",
                pageNum, pageSize, username, realName, phone, role, status);
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("realName", realName);
        params.put("phone", phone);
        params.put("role", role);
        params.put("status", status);

        // 调用service层方法进行分页查询
        Page<SysUser> pageResult = sysUserService.getSysUserPage(pageNum, pageSize, params);
        return ApiResponse.success(pageResult);
    }
}