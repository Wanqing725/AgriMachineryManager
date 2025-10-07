package org.agrimachinerymanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.entity.Machinery;
import org.agrimachinerymanager.service.MachineryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 农机档案控制器
 */
@RestController
@RequestMapping("/machinery")
@Tag(name = "农机档案管理", description = "农机档案的增删改查操作")
public class MachineryController {
    private static final Logger log = LoggerFactory.getLogger(MachineryController.class);
    @Autowired
    private MachineryService machineryService;

    /**
     * 获取所有农机档案
     * @return 农机档案列表
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllMachinery")
    @Operation(summary = "获取所有农机档案", description = "查询系统中所有的农机档案信息")
    public ApiResponse<List<Machinery>> getAllMachinery(){
        // 调用service层方法获取所有农机档案
        List<Machinery> machineryList = machineryService.getAllMachinery();
        log.info("获取所有农机档案");
        return ApiResponse.success(machineryList);
    }
    
    /**
     * 根据ID获取农机档案
     * @param id 农机ID
     * @return 农机档案信息
     */
    @GetMapping("/getMachineryById/{id}")
    @Operation(summary = "根据ID获取农机档案", description = "根据农机ID查询具体的农机档案信息")
    public ApiResponse<Machinery> getMachineryById(
            @Parameter(description = "农机ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("获取农机档案，ID：{}", id);
        Machinery machinery = machineryService.getMachineryById(id);
        return ApiResponse.success(machinery);
    }
    
    /**
     * 新增农机档案
     * @param machinery 农机档案信息
     * @return 操作结果
     */
    @PostMapping("/addMachinery")
    @Operation(summary = "新增农机档案", description = "添加新的农机档案信息")
    public ApiResponse<Machinery> addMachinery(
            @Parameter(description = "农机档案信息", required = true)
            @RequestBody Machinery machinery) {
        log.info("新增农机档案：{}", machinery);
        machineryService.addMachinery(machinery);
        return ApiResponse.success(machinery);
    }
    
    /**
     * 更新农机档案
     * @param machinery 农机档案信息
     * @return 操作结果
     */
    @PutMapping("/updateMachinery")
    @Operation(summary = "更新农机档案", description = "更新已有的农机档案信息")
    public ApiResponse<Machinery> updateMachinery(
            @Parameter(description = "农机档案信息", required = true)
            @RequestBody Machinery machinery) {
        log.info("更新农机档案：{}", machinery);
        machineryService.updateMachinery(machinery);
        return ApiResponse.success(machinery);
    }
    
    /**
     * 删除农机档案
     * @param id 农机ID
     * @return 操作结果
     */
    @DeleteMapping("/deleteMachinery/{id}")
    @Operation(summary = "删除农机档案", description = "根据ID删除农机档案")
    public ApiResponse<Boolean> deleteMachinery(
            @Parameter(description = "农机ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("删除农机档案，ID：{}", id);
        machineryService.deleteMachinery(id);
        return ApiResponse.success(true);
    }
    
    /**
     * 分页查询农机档案
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param machineryCode 农机编号
     * @param brand 品牌
     * @param model 型号
     * @param status 状态
     * @param department 部门
     * @return 分页结果
     */
    @GetMapping("/getMachineryPage")
    @Operation(summary = "分页查询农机档案", description = "分页查询农机档案，支持条件筛选")
    public ApiResponse<Page<Machinery>> getMachineryPage(
            @Parameter(description = "页码", required = true, example = "1")
            @RequestParam("pageNum") int pageNum,
            
            @Parameter(description = "每页条数", required = true, example = "10")
            @RequestParam("pageSize") int pageSize,
            
            @Parameter(description = "农机编号")
            @RequestParam(value = "machineryCode", required = false) String machineryCode,
            
            @Parameter(description = "品牌")
            @RequestParam(value = "brand", required = false) String brand,
            
            @Parameter(description = "型号")
            @RequestParam(value = "model", required = false) String model,
            
            @Parameter(description = "状态")
            @RequestParam(value = "status", required = false) String status,
            
            @Parameter(description = "归属部门")
            @RequestParam(value = "department", required = false) String department) {
        log.info("分页查询农机档案，页码：{}，每页条数：{}", pageNum, pageSize);
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("machineryCode", machineryCode);
        params.put("brand", brand);
        params.put("model", model);
        params.put("status", status);
        params.put("department", department);
        
        // 调用分页查询方法
        Page<Machinery> pageResult = machineryService.getMachineryPage(pageNum, pageSize, params);
        
        return ApiResponse.success(pageResult);
    }
}