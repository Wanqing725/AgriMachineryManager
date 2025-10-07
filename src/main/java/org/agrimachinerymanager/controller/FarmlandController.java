package org.agrimachinerymanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.entity.Farmland;
import org.agrimachinerymanager.service.FarmlandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地块信息控制器
 */
@RestController
@RequestMapping("/farmland")
@Tag(name = "地块信息管理", description = "地块信息的增删改查操作")
public class FarmlandController {
    private static final Logger log = LoggerFactory.getLogger(FarmlandController.class);
    
    @Autowired
    private FarmlandService farmlandService;
    
    /**
     * 获取所有地块信息
     * @return 地块信息列表
     */
    @GetMapping("/getAllFarmlands")
    @Operation(summary = "获取所有地块信息", description = "查询系统中所有的地块信息")
    public ApiResponse<List<Farmland>> getAllFarmlands(){
        // 调用service层方法获取所有地块信息
        List<Farmland> farmlandList = farmlandService.getAllFarmlands();
        return ApiResponse.success(farmlandList);
    }
    
    /**
     * 根据ID获取地块信息
     * @param id 地块ID
     * @return 地块信息
     */
    @GetMapping("/getFarmlandById/{id}")
    @Operation(summary = "根据ID获取地块信息", description = "根据地块ID查询具体的地块信息")
    public ApiResponse<Farmland> getFarmlandById(
            @Parameter(description = "地块ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("获取地块信息，ID：{}", id);
        Farmland farmland = farmlandService.getFarmlandById(id);
        return ApiResponse.success(farmland);
    }
    
    /**
     * 新增地块信息
     * @param farmland 地块信息
     * @return 操作结果
     */
    @PostMapping("/addFarmland")
    @Operation(summary = "新增地块信息", description = "添加新的地块信息")
    public ApiResponse<Farmland> addFarmland(
            @Parameter(description = "地块信息", required = true)
            @RequestBody Farmland farmland) {
        log.info("新增地块信息：{}", farmland);
        farmlandService.addFarmland(farmland);
        return ApiResponse.success(farmland);
    }
    
    /**
     * 更新地块信息
     * @param farmland 地块信息
     * @return 操作结果
     */
    @PutMapping("/updateFarmland")
    @Operation(summary = "更新地块信息", description = "更新已有的地块信息")
    public ApiResponse<Farmland> updateFarmland(
            @Parameter(description = "地块信息", required = true)
            @RequestBody Farmland farmland) {
        log.info("更新地块信息：{}", farmland);
        farmlandService.updateFarmland(farmland);
        return ApiResponse.success(farmland);
    }
    
    /**
     * 删除地块信息
     * @param id 地块ID
     * @return 操作结果
     */
    @DeleteMapping("/deleteFarmland/{id}")
    @Operation(summary = "删除地块信息", description = "根据ID删除地块信息")
    public ApiResponse<Boolean> deleteFarmland(
            @Parameter(description = "地块ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("删除地块信息，ID：{}", id);
        farmlandService.deleteFarmland(id);
        return ApiResponse.success(true);
    }
    
    /**
     * 分页查询地块信息
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param landCode 地块编码
     * @param name 地块名称
     * @param location 位置
     * @return 分页结果
     */
    @GetMapping("/getFarmlandPage")
    @Operation(summary = "分页查询地块信息", description = "分页查询地块信息，支持条件筛选")
    public ApiResponse<Page<Farmland>> getFarmlandPage(
            @Parameter(description = "页码", required = true, example = "1")
            @RequestParam("pageNum") int pageNum,
            
            @Parameter(description = "每页条数", required = true, example = "10")
            @RequestParam("pageSize") int pageSize,
            
            @Parameter(description = "地块编码")
            @RequestParam(value = "landCode", required = false) String landCode,
            
            @Parameter(description = "地块名称")
            @RequestParam(value = "name", required = false) String name,
            
            @Parameter(description = "位置")
            @RequestParam(value = "location", required = false) String location) {
        log.info("分页查询地块信息，页码：{}，每页条数：{}", pageNum, pageSize);
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("landCode", landCode);
        params.put("name", name);
        params.put("location", location);
        
        // 调用分页查询方法
        Page<Farmland> pageResult = farmlandService.getFarmlandPage(pageNum, pageSize, params);
        
        return ApiResponse.success(pageResult);
    }
}