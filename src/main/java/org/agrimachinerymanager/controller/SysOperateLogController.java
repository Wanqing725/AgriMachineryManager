package org.agrimachinerymanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.entity.SysOperateLog;
import org.agrimachinerymanager.service.SysOperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统操作日志控制器
 */
@RestController
@RequestMapping("/sys-operate-log")
@Tag(name = "系统操作日志管理", description = "系统操作日志的增删改查操作")
public class SysOperateLogController {
    private static final Logger log = LoggerFactory.getLogger(SysOperateLogController.class);
    
    @Autowired
    private SysOperateLogService sysOperateLogService;

    /**
     * 获取所有系统操作日志
     * @return 系统操作日志列表
     */
    @GetMapping("/getAllSysOperateLogs")
    @Operation(summary = "获取所有系统操作日志", description = "查询系统中所有的操作日志信息")
    public ApiResponse<List<SysOperateLog>> getAllSysOperateLogs() {
        log.info("获取所有系统操作日志");
        List<SysOperateLog> sysOperateLogs = sysOperateLogService.getAllSysOperateLogs();
        return ApiResponse.success(sysOperateLogs);
    }

    /**
     * 根据ID获取系统操作日志
     * @param id 日志ID
     * @return 系统操作日志
     */
    @GetMapping("/getSysOperateLogById/{id}")
    @Operation(summary = "根据ID获取系统操作日志", description = "根据日志ID查询系统操作日志的详细信息")
    public ApiResponse<SysOperateLog> getSysOperateLogById(
            @Parameter(description = "日志ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("根据ID获取系统操作日志: {}", id);
        SysOperateLog sysOperateLog = sysOperateLogService.getSysOperateLogById(id);
        return ApiResponse.success(sysOperateLog);
    }

    /**
     * 添加系统操作日志
     * @param sysOperateLog 系统操作日志信息
     * @return 操作结果
     */
    @PostMapping("/addSysOperateLog")
    @Operation(summary = "添加系统操作日志", description = "添加新的系统操作日志")
    public ApiResponse<SysOperateLog> addSysOperateLog(
            @Parameter(description = "系统操作日志信息", required = true)
            @RequestBody SysOperateLog sysOperateLog) {
        log.info("添加系统操作日志: {}", sysOperateLog);
        sysOperateLogService.addSysOperateLog(sysOperateLog);
        return ApiResponse.success(sysOperateLog);
    }

    /**
     * 更新系统操作日志
     * @param sysOperateLog 系统操作日志信息
     * @return 操作结果
     */
    @PutMapping("/updateSysOperateLog")
    @Operation(summary = "更新系统操作日志", description = "更新已有的系统操作日志信息")
    public ApiResponse<Boolean> updateSysOperateLog(
            @Parameter(description = "系统操作日志信息", required = true)
            @RequestBody SysOperateLog sysOperateLog) {
        log.info("更新系统操作日志: {}", sysOperateLog);
        boolean result = sysOperateLogService.updateSysOperateLog(sysOperateLog);
        return ApiResponse.success(result);
    }

    /**
     * 删除系统操作日志
     * @param id 日志ID
     * @return 操作结果
     */
    @DeleteMapping("/deleteSysOperateLog/{id}")
    @Operation(summary = "删除系统操作日志", description = "根据ID删除系统操作日志")
    public ApiResponse<Boolean> deleteSysOperateLog(
            @Parameter(description = "日志ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("删除系统操作日志: {}", id);
        boolean result = sysOperateLogService.deleteSysOperateLog(id);
        return ApiResponse.success(result);
    }

    /**
     * 分页查询系统操作日志
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param userId 用户ID
     * @param operateType 操作类型
     * @param operateModule 操作模块
     * @param operateContent 操作内容
     * @param operateIp 操作IP
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    @GetMapping("/getSysOperateLogPage")
    @Operation(summary = "分页查询系统操作日志", description = "分页查询系统操作日志列表")
    public ApiResponse<Page<SysOperateLog>> getSysOperateLogPage(
            @Parameter(description = "页码", required = true, example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数", required = true, example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "用户ID")
            @RequestParam(required = false) Long userId,
            @Parameter(description = "操作类型")
            @RequestParam(required = false) String operateType,
            @Parameter(description = "操作模块")
            @RequestParam(required = false) String operateModule,
            @Parameter(description = "操作内容")
            @RequestParam(required = false) String operateContent,
            @Parameter(description = "操作IP")
            @RequestParam(required = false) String operateIp,
            @Parameter(description = "开始时间")
            @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间")
            @RequestParam(required = false) String endTime) {
        log.info("分页查询系统操作日志，页码：{}，每页条数：{}", pageNum, pageSize);
        
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        if (userId != null) {
            params.put("userId", userId);
        }
        if (operateType != null && !operateType.isEmpty()) {
            params.put("operateType", operateType);
        }
        if (operateModule != null && !operateModule.isEmpty()) {
            params.put("operateModule", operateModule);
        }
        if (operateContent != null && !operateContent.isEmpty()) {
            params.put("operateContent", operateContent);
        }
        if (operateIp != null && !operateIp.isEmpty()) {
            params.put("operateIp", operateIp);
        }
        if (startTime != null && !startTime.isEmpty()) {
            params.put("startTime", startTime);
        }
        if (endTime != null && !endTime.isEmpty()) {
            params.put("endTime", endTime);
        }
        
        Page<SysOperateLog> page = sysOperateLogService.getSysOperateLogPage(pageNum, pageSize, params);
        return ApiResponse.success(page);
    }

    /**
     * 根据用户ID查询系统操作日志
     * @param userId 用户ID
     * @return 系统操作日志列表
     */
    @GetMapping("/getSysOperateLogsByUserId/{userId}")
    @Operation(summary = "根据用户ID查询系统操作日志", description = "根据用户ID查询相关的系统操作日志")
    public ApiResponse<List<SysOperateLog>> getSysOperateLogsByUserId(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable("userId") Long userId) {
        log.info("根据用户ID查询系统操作日志: {}", userId);
        List<SysOperateLog> sysOperateLogs = sysOperateLogService.getSysOperateLogsByUserId(userId);
        return ApiResponse.success(sysOperateLogs);
    }

    /**
     * 根据操作类型查询系统操作日志
     * @param operateType 操作类型
     * @return 系统操作日志列表
     */
    @GetMapping("/getSysOperateLogsByType/{operateType}")
    @Operation(summary = "根据操作类型查询系统操作日志", description = "根据操作类型查询相关的系统操作日志")
    public ApiResponse<List<SysOperateLog>> getSysOperateLogsByType(
            @Parameter(description = "操作类型", required = true, example = "add")
            @PathVariable("operateType") String operateType) {
        log.info("根据操作类型查询系统操作日志: {}", operateType);
        List<SysOperateLog> sysOperateLogs = sysOperateLogService.getSysOperateLogsByType(operateType);
        return ApiResponse.success(sysOperateLogs);
    }

    /**
     * 根据操作模块查询系统操作日志
     * @param operateModule 操作模块
     * @return 系统操作日志列表
     */
    @GetMapping("/getSysOperateLogsByModule/{operateModule}")
    @Operation(summary = "根据操作模块查询系统操作日志", description = "根据操作模块查询相关的系统操作日志")
    public ApiResponse<List<SysOperateLog>> getSysOperateLogsByModule(
            @Parameter(description = "操作模块", required = true, example = "machinery")
            @PathVariable("operateModule") String operateModule) {
        log.info("根据操作模块查询系统操作日志: {}", operateModule);
        List<SysOperateLog> sysOperateLogs = sysOperateLogService.getSysOperateLogsByModule(operateModule);
        return ApiResponse.success(sysOperateLogs);
    }
}