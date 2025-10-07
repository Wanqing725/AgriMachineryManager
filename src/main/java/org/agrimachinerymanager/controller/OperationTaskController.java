package org.agrimachinerymanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.entity.OperationTask;
import org.agrimachinerymanager.service.OperationTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作业调度任务控制器
 */
@RestController
@RequestMapping("/operation-task")
@Tag(name = "作业调度任务管理", description = "作业调度任务的增删改查操作")
public class OperationTaskController {
    private static final Logger log = LoggerFactory.getLogger(OperationTaskController.class);
    
    @Autowired
    private OperationTaskService operationTaskService;

    /**
     * 获取所有作业调度任务
     * @return 作业调度任务列表
     */
    @GetMapping("/getAllOperationTasks")
    @Operation(summary = "获取所有作业调度任务", description = "查询系统中所有的作业调度任务信息")
    public ApiResponse<List<OperationTask>> getAllOperationTasks() {
        log.info("获取所有作业调度任务");
        List<OperationTask> operationTasks = operationTaskService.getAllOperationTasks();
        return ApiResponse.success(operationTasks);
    }

    /**
     * 根据ID获取作业调度任务
     * @param id 任务ID
     * @return 作业调度任务
     */
    @GetMapping("/getOperationTaskById/{id}")
    @Operation(summary = "根据ID获取作业调度任务", description = "根据任务ID查询作业调度任务的详细信息")
    public ApiResponse<OperationTask> getOperationTaskById(
            @Parameter(description = "任务ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("根据ID获取作业调度任务: {}", id);
        OperationTask operationTask = operationTaskService.getOperationTaskById(id);
        return ApiResponse.success(operationTask);
    }

    /**
     * 新增作业调度任务
     * @param operationTask 作业调度任务信息
     * @return 操作结果
     */
    @PostMapping("/addOperationTask")
    @Operation(summary = "新增作业调度任务", description = "添加新的作业调度任务")
    public ApiResponse<OperationTask> addOperationTask(
            @Parameter(description = "作业调度任务信息", required = true)
            @RequestBody OperationTask operationTask) {
        log.info("新增作业调度任务: {}", operationTask);
        operationTaskService.addOperationTask(operationTask);
        return ApiResponse.success(operationTask);
    }

    /**
     * 更新作业调度任务
     * @param operationTask 作业调度任务信息
     * @return 操作结果
     */
    @PutMapping("/updateOperationTask")
    @Operation(summary = "更新作业调度任务", description = "更新已有的作业调度任务信息")
    public ApiResponse<Boolean> updateOperationTask(
            @Parameter(description = "作业调度任务信息", required = true)
            @RequestBody OperationTask operationTask) {
        log.info("更新作业调度任务: {}", operationTask);
        boolean result = operationTaskService.updateOperationTask(operationTask);
        return ApiResponse.success(result);
    }

    /**
     * 删除作业调度任务
     * @param id 任务ID
     * @return 操作结果
     */
    @DeleteMapping("/deleteOperationTask/{id}")
    @Operation(summary = "删除作业调度任务", description = "根据ID删除作业调度任务")
    public ApiResponse<Boolean> deleteOperationTask(
            @Parameter(description = "任务ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("删除作业调度任务: {}", id);
        boolean result = operationTaskService.deleteOperationTask(id);
        return ApiResponse.success(result);
    }

    /**
     * 分页查询作业调度任务
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param taskCode 任务编码
     * @param machineryId 农机ID
     * @param farmlandId 地块ID
     * @param operationType 作业类型
     * @param status 状态
     * @param responsibleUserId 负责人ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 分页结果
     */
    @GetMapping("/getOperationTaskPage")
    @Operation(summary = "分页查询作业调度任务", description = "分页查询作业调度任务列表")
    public ApiResponse<Page<OperationTask>> getOperationTaskPage(
            @Parameter(description = "页码", required = true, example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数", required = true, example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "任务编码")
            @RequestParam(required = false) String taskCode,
            @Parameter(description = "农机ID")
            @RequestParam(required = false) Long machineryId,
            @Parameter(description = "地块ID")
            @RequestParam(required = false) Long farmlandId,
            @Parameter(description = "作业类型")
            @RequestParam(required = false) String operationType,
            @Parameter(description = "状态")
            @RequestParam(required = false) Integer status,
            @Parameter(description = "负责人ID")
            @RequestParam(required = false) Long responsibleUserId,
            @Parameter(description = "开始日期")
            @RequestParam(required = false) String startDate,
            @Parameter(description = "结束日期")
            @RequestParam(required = false) String endDate) {
        log.info("分页查询作业调度任务，页码：{}，每页条数：{}", pageNum, pageSize);
        
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        if (taskCode != null && !taskCode.isEmpty()) {
            params.put("taskCode", taskCode);
        }
        if (machineryId != null) {
            params.put("machineryId", machineryId);
        }
        if (farmlandId != null) {
            params.put("farmlandId", farmlandId);
        }
        if (operationType != null && !operationType.isEmpty()) {
            params.put("operationType", operationType);
        }
        if (status != null) {
            params.put("status", status);
        }
        if (responsibleUserId != null) {
            params.put("responsibleUserId", responsibleUserId);
        }
        if (startDate != null && !startDate.isEmpty()) {
            params.put("startDate", startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            params.put("endDate", endDate);
        }
        
        Page<OperationTask> page = operationTaskService.getOperationTaskPage(pageNum, pageSize, params);
        return ApiResponse.success(page);
    }

    /**
     * 根据农机ID查询作业调度任务
     * @param machineryId 农机ID
     * @return 作业调度任务列表
     */
    @GetMapping("/getOperationTasksByMachineryId/{machineryId}")
    @Operation(summary = "根据农机ID查询作业调度任务", description = "根据农机ID查询相关的作业调度任务")
    public ApiResponse<List<OperationTask>> getOperationTasksByMachineryId(
            @Parameter(description = "农机ID", required = true, example = "1")
            @PathVariable("machineryId") Long machineryId) {
        log.info("根据农机ID查询作业调度任务: {}", machineryId);
        List<OperationTask> operationTasks = operationTaskService.getOperationTasksByMachineryId(machineryId);
        return ApiResponse.success(operationTasks);
    }

    /**
     * 根据地块ID查询作业调度任务
     * @param farmlandId 地块ID
     * @return 作业调度任务列表
     */
    @GetMapping("/getOperationTasksByFarmlandId/{farmlandId}")
    @Operation(summary = "根据地块ID查询作业调度任务", description = "根据地块ID查询相关的作业调度任务")
    public ApiResponse<List<OperationTask>> getOperationTasksByFarmlandId(
            @Parameter(description = "地块ID", required = true, example = "1")
            @PathVariable("farmlandId") Long farmlandId) {
        log.info("根据地块ID查询作业调度任务: {}", farmlandId);
        List<OperationTask> operationTasks = operationTaskService.getOperationTasksByFarmlandId(farmlandId);
        return ApiResponse.success(operationTasks);
    }

    /**
     * 根据状态查询作业调度任务
     * @param status 状态：1-待执行，2-执行中，3-已完成，4-已取消
     * @return 作业调度任务列表
     */
    @GetMapping("/getOperationTasksByStatus/{status}")
    @Operation(summary = "根据状态查询作业调度任务", description = "根据任务状态查询作业调度任务")
    public ApiResponse<List<OperationTask>> getOperationTasksByStatus(
            @Parameter(description = "任务状态", required = true, example = "1")
            @PathVariable("status") Integer status) {
        log.info("根据状态查询作业调度任务: {}", status);
        List<OperationTask> operationTasks = operationTaskService.getOperationTasksByStatus(status);
        return ApiResponse.success(operationTasks);
    }
}