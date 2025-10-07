package org.agrimachinerymanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.entity.SysDict;
import org.agrimachinerymanager.service.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典控制器
 */
@RestController
@RequestMapping("/sys-dict")
@Tag(name = "数据字典管理", description = "数据字典的增删改查操作")
public class SysDictController {
    private static final Logger log = LoggerFactory.getLogger(SysDictController.class);
    
    @Autowired
    private SysDictService sysDictService;

    /**
     * 获取所有数据字典
     * @return 数据字典列表
     */
    @GetMapping("/getAllSysDicts")
    @Operation(summary = "获取所有数据字典", description = "查询系统中所有的数据字典信息")
    public ApiResponse<List<SysDict>> getAllSysDicts() {
        log.info("获取所有数据字典");
        List<SysDict> dictList = sysDictService.getAllSysDicts();
        return ApiResponse.success(dictList);
    }

    /**
     * 根据ID获取数据字典
     * @param id 字典ID
     * @return 数据字典
     */
    @GetMapping("/getSysDictById/{id}")
    @Operation(summary = "根据ID获取数据字典", description = "根据字典ID查询数据字典的详细信息")
    public ApiResponse<SysDict> getSysDictById(
            @Parameter(description = "字典ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("根据ID获取数据字典: {}", id);
        SysDict sysDict = sysDictService.getSysDictById(id);
        return ApiResponse.success(sysDict);
    }

    /**
     * 新增数据字典
     * @param sysDict 数据字典信息
     * @return 操作结果
     */
    @PostMapping("/addSysDict")
    @Operation(summary = "新增数据字典", description = "添加新的数据字典")
    public ApiResponse<SysDict> addSysDict(
            @Parameter(description = "数据字典信息", required = true)
            @RequestBody SysDict sysDict) {
        log.info("新增数据字典: {}", sysDict);
        sysDictService.addSysDict(sysDict);
        return ApiResponse.success(sysDict);
    }

    /**
     * 更新数据字典
     * @param sysDict 数据字典信息
     * @return 操作结果
     */
    @PutMapping("/updateSysDict")
    @Operation(summary = "更新数据字典", description = "更新已有的数据字典信息")
    public ApiResponse<Boolean> updateSysDict(
            @Parameter(description = "数据字典信息", required = true)
            @RequestBody SysDict sysDict) {
        log.info("更新数据字典: {}", sysDict);
        sysDictService.updateSysDict(sysDict);
        return ApiResponse.success(true);
    }

    /**
     * 删除数据字典
     * @param id 字典ID
     * @return 操作结果
     */
    @DeleteMapping("/deleteSysDict/{id}")
    @Operation(summary = "删除数据字典", description = "根据ID删除数据字典")
    public ApiResponse<Boolean> deleteSysDict(
            @Parameter(description = "字典ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("删除数据字典: {}", id);
        sysDictService.deleteSysDict(id);
        return ApiResponse.success(true);
    }

    /**
     * 分页查询数据字典
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param type 字典类型（可选）
     * @param code 字典编码（可选）
     * @param name 字典名称（可选）
     * @return 分页结果
     */
    @GetMapping("/getSysDictPage")
    @Operation(summary = "分页查询数据字典", description = "分页查询数据字典列表，支持条件筛选")
    public ApiResponse<Page<SysDict>> getSysDictPage(
            @Parameter(description = "页码", required = true, example = "1")
            @RequestParam("pageNum") int pageNum,
            @Parameter(description = "每页条数", required = true, example = "10")
            @RequestParam("pageSize") int pageSize,
            @Parameter(description = "字典类型")
            @RequestParam(value = "type", required = false) String type,
            @Parameter(description = "字典编码")
            @RequestParam(value = "code", required = false) String code,
            @Parameter(description = "字典名称")
            @RequestParam(value = "name", required = false) String name) {
        log.info("分页查询数据字典: pageNum={}, pageSize={}, type={}, code={}, name={}",
                pageNum, pageSize, type, code, name);
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("code", code);
        params.put("name", name);

        // 调用service层方法进行分页查询
        Page<SysDict> pageResult = sysDictService.getSysDictPage(pageNum, pageSize, params);
        return ApiResponse.success(pageResult);
    }
    
    /**
     * 根据字典类型获取字典数据
     * @param type 字典类型
     * @return 字典数据列表
     */
    @GetMapping("/getSysDictByType/{type}")
    @Operation(summary = "根据类型获取字典数据", description = "根据字典类型查询字典数据列表")
    public ApiResponse<List<SysDict>> getSysDictByType(
            @Parameter(description = "字典类型", required = true, example = "machinery_type")
            @PathVariable("type") String type) {
        log.info("根据字典类型获取字典数据: {}", type);
        List<SysDict> dictList = sysDictService.getSysDictByType(type);
        return ApiResponse.success(dictList);
    }
    
    /**
     * 根据字典类型和编码获取字典数据
     * @param type 字典类型
     * @param code 字典编码
     * @return 字典数据
     */
    @GetMapping("/getSysDictByTypeAndCode")
    @Operation(summary = "根据类型和编码获取字典数据", description = "根据字典类型和编码查询具体的字典数据")
    public ApiResponse<SysDict> getSysDictByTypeAndCode(
            @Parameter(description = "字典类型", required = true, example = "machinery_type")
            @RequestParam("type") String type,
            @Parameter(description = "字典编码", required = true, example = "TRACTOR")
            @RequestParam("code") String code) {
        log.info("根据字典类型和编码获取字典数据: type={}, code={}", type, code);
        SysDict sysDict = sysDictService.getSysDictByTypeAndCode(type, code);
        return ApiResponse.success(sysDict);
    }
}