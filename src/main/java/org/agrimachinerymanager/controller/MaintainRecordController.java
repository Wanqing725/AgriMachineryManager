package org.agrimachinerymanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.entity.MaintainRecord;
import org.agrimachinerymanager.service.MaintainRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 农机维护记录控制器
 */
@RestController
@RequestMapping("/maintain-record")
@Tag(name = "农机维护记录管理", description = "农机维护记录的增删改查操作")
public class MaintainRecordController {

    private static final Logger log = LoggerFactory.getLogger(MaintainRecordController.class);

    @Autowired
    private MaintainRecordService maintainRecordService;

    /**
     * 获取所有农机维护记录
     * @return 农机维护记录列表
     */
    @GetMapping("/getAllMaintainRecords")
    @Operation(summary = "获取所有农机维护记录", description = "查询系统中所有的农机维护记录信息")
    public ApiResponse<List<MaintainRecord>> getAllMaintainRecords() {
        log.info("获取所有农机维护记录");
        List<MaintainRecord> maintainRecords = maintainRecordService.getAllMaintainRecords();
        return ApiResponse.success(maintainRecords);
    }

    /**
     * 根据ID获取农机维护记录
     * @param id 记录ID
     * @return 农机维护记录
     */
    @GetMapping("/getMaintainRecordById/{id}")
    @Operation(summary = "根据ID获取农机维护记录", description = "根据记录ID查询农机维护记录的详细信息")
    public ApiResponse<MaintainRecord> getMaintainRecordById(
            @Parameter(description = "记录ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("根据ID获取农机维护记录: {}", id);
        MaintainRecord maintainRecord = maintainRecordService.getMaintainRecordById(id);
        return ApiResponse.success(maintainRecord);
    }

    /**
     * 新增农机维护记录
     * @param maintainRecord 农机维护记录信息
     * @return 操作结果
     */
    @PostMapping("/addMaintainRecord")
    @Operation(summary = "新增农机维护记录", description = "添加新的农机维护记录")
    public ApiResponse<Boolean> addMaintainRecord(
            @Parameter(description = "农机维护记录信息", required = true)
            @RequestBody MaintainRecord maintainRecord) {
        log.info("新增农机维护记录: {}", maintainRecord);
        boolean result = maintainRecordService.addMaintainRecord(maintainRecord);
        return ApiResponse.success(result);
    }

    /**
     * 更新农机维护记录
     * @param maintainRecord 农机维护记录信息
     * @return 操作结果
     */
    @PutMapping("/updateMaintainRecord")
    @Operation(summary = "更新农机维护记录", description = "更新已有的农机维护记录信息")
    public ApiResponse<Boolean> updateMaintainRecord(
            @Parameter(description = "农机维护记录信息", required = true)
            @RequestBody MaintainRecord maintainRecord) {
        log.info("更新农机维护记录: {}", maintainRecord);
        boolean result = maintainRecordService.updateMaintainRecord(maintainRecord);
        return ApiResponse.success(result);
    }

    /**
     * 删除农机维护记录
     * @param id 记录ID
     * @return 操作结果
     */
    @DeleteMapping("/deleteMaintainRecord/{id}")
    @Operation(summary = "删除农机维护记录", description = "根据ID删除农机维护记录")
    public ApiResponse<Boolean> deleteMaintainRecord(
            @Parameter(description = "记录ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("删除农机维护记录: {}", id);
        boolean result = maintainRecordService.deleteMaintainRecord(id);
        return ApiResponse.success(result);
    }

    /**
     * 分页查询农机维护记录
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param machineryId 农机ID
     * @param maintainType 维护类型
     * @param maintainer 维护人员
     * @param createUserId 创建人ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param description 维护描述
     * @param minCost 最小费用
     * @param maxCost 最大费用
     * @return 分页结果
     */
    @GetMapping("/getMaintainRecordPage")
    @Operation(summary = "分页查询农机维护记录", description = "分页查询农机维护记录列表")
    public ApiResponse<Page<MaintainRecord>> getMaintainRecordPage(
            @Parameter(description = "页码", required = true, example = "1")
            @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页条数", required = true, example = "10")
            @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "农机ID")
            @RequestParam(required = false) Long machineryId,
            @Parameter(description = "维护类型")
            @RequestParam(required = false) String maintainType,
            @Parameter(description = "维护人员")
            @RequestParam(required = false) String maintainer,
            @Parameter(description = "创建人ID")
            @RequestParam(required = false) Long createUserId,
            @Parameter(description = "开始时间")
            @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间")
            @RequestParam(required = false) String endTime,
            @Parameter(description = "维护描述")
            @RequestParam(required = false) String description,
            @Parameter(description = "最小费用")
            @RequestParam(required = false) BigDecimal minCost,
            @Parameter(description = "最大费用")
            @RequestParam(required = false) BigDecimal maxCost) {
        log.info("分页查询农机维护记录，页码：{}，每页条数：{}", pageNum, pageSize);
        
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        if (machineryId != null) {
            params.put("machineryId", machineryId);
        }
        if (maintainType != null && !maintainType.isEmpty()) {
            params.put("maintainType", maintainType);
        }
        if (maintainer != null && !maintainer.isEmpty()) {
            params.put("maintainer", maintainer);
        }
        if (createUserId != null) {
            params.put("createUserId", createUserId);
        }
        if (startTime != null && !startTime.isEmpty()) {
            params.put("startTime", startTime);
        }
        if (endTime != null && !endTime.isEmpty()) {
            params.put("endTime", endTime);
        }
        if (description != null && !description.isEmpty()) {
            params.put("description", description);
        }
        if (minCost != null) {
            params.put("minCost", minCost);
        }
        if (maxCost != null) {
            params.put("maxCost", maxCost);
        }
        
        Page<MaintainRecord> page = maintainRecordService.getMaintainRecordPage(pageNum, pageSize, params);
        return ApiResponse.success(page);
    }

    /**
     * 根据农机ID查询维护记录
     * @param machineryId 农机ID
     * @return 维护记录列表
     */
    @GetMapping("/getMaintainRecordsByMachineryId/{machineryId}")
    @Operation(summary = "根据农机ID查询维护记录", description = "根据农机ID查询相关的维护记录")
    public ApiResponse<List<MaintainRecord>> getMaintainRecordsByMachineryId(
            @Parameter(description = "农机ID", required = true, example = "1")
            @PathVariable("machineryId") Long machineryId) {
        log.info("根据农机ID查询维护记录: {}", machineryId);
        List<MaintainRecord> maintainRecords = maintainRecordService.getMaintainRecordsByMachineryId(machineryId);
        return ApiResponse.success(maintainRecords);
    }

    /**
     * 根据维护类型查询维护记录
     * @param maintainType 维护类型
     * @return 维护记录列表
     */
    @GetMapping("/getMaintainRecordsByType/{maintainType}")
    @Operation(summary = "根据维护类型查询维护记录", description = "根据维护类型查询相关的维护记录")
    public ApiResponse<List<MaintainRecord>> getMaintainRecordsByType(
            @Parameter(description = "维护类型", required = true, example = "常规保养")
            @PathVariable("maintainType") String maintainType) {
        log.info("根据维护类型查询维护记录: {}", maintainType);
        List<MaintainRecord> maintainRecords = maintainRecordService.getMaintainRecordsByType(maintainType);
        return ApiResponse.success(maintainRecords);
    }

    /**
     * 根据创建人ID查询维护记录
     * @param createUserId 创建人ID
     * @return 维护记录列表
     */
    @GetMapping("/getMaintainRecordsByCreateUserId/{createUserId}")
    @Operation(summary = "根据创建人ID查询维护记录", description = "根据创建人ID查询相关的维护记录")
    public ApiResponse<List<MaintainRecord>> getMaintainRecordsByCreateUserId(
            @Parameter(description = "创建人ID", required = true, example = "1")
            @PathVariable("createUserId") Long createUserId) {
        log.info("根据创建人ID查询维护记录: {}", createUserId);
        List<MaintainRecord> maintainRecords = maintainRecordService.getMaintainRecordsByCreateUserId(createUserId);
        return ApiResponse.success(maintainRecords);
    }

    /**
     * 根据维护时间范围查询维护记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 维护记录列表
     */
    @GetMapping("/getMaintainRecordsByTimeRange")
    @Operation(summary = "根据维护时间范围查询维护记录", description = "根据指定的时间范围查询维护记录")
    public ApiResponse<List<MaintainRecord>> getMaintainRecordsByTimeRange(
            @Parameter(description = "开始时间", required = true, example = "2023-01-01 00:00:00")
            @RequestParam String startTime,
            @Parameter(description = "结束时间", required = true, example = "2023-12-31 23:59:59")
            @RequestParam String endTime) {
        log.info("根据维护时间范围查询维护记录，开始时间：{}，结束时间：{}", startTime, endTime);
        List<MaintainRecord> maintainRecords = maintainRecordService.getMaintainRecordsByTimeRange(startTime, endTime);
        return ApiResponse.success(maintainRecords);
    }
}