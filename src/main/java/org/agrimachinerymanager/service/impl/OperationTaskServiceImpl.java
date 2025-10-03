package org.agrimachinerymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.OperationTask;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.mapper.OperationTaskMapper;
import org.agrimachinerymanager.service.OperationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 作业调度任务服务实现类
 */
@Service
public class OperationTaskServiceImpl implements OperationTaskService {

    @Autowired
    private OperationTaskMapper operationTaskMapper;

    /**
     * 获取所有作业调度任务
     * @return 作业调度任务列表
     */
    @Override
    public List<OperationTask> getAllOperationTasks() {
        // 使用MyBatis-Plus的QueryWrapper构建查询条件
        QueryWrapper<OperationTask> queryWrapper = new QueryWrapper<>();
        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");

        // 调用mapper的selectList方法查询所有作业调度任务
        return operationTaskMapper.selectList(queryWrapper);
    }

    /**
     * 根据ID获取作业调度任务
     * @param id 任务ID
     * @return 作业调度任务
     */
    @Override
    public OperationTask getOperationTaskById(Long id) {
        OperationTask operationTask = operationTaskMapper.selectById(id);
        if (operationTask == null) {
            throw new BaseException("未找到ID为" + id + "的作业调度任务");
        }
        return operationTask;
    }

    /**
     * 新增作业调度任务
     * @param operationTask 作业调度任务信息
     * @return 是否新增成功
     */
    @Override
    public boolean addOperationTask(OperationTask operationTask) {
        // 进行业务验证
        if (operationTask == null) {
            throw new BaseException("作业调度任务信息不能为空");
        }
        
        if (operationTask.getMachineryId() == null) {
            throw new BaseException("农机ID不能为空");
        }
        
        if (operationTask.getFarmlandId() == null) {
            throw new BaseException("地块ID不能为空");
        }
        
        if (operationTask.getOperationType() == null || operationTask.getOperationType().trim().isEmpty()) {
            throw new BaseException("作业类型不能为空");
        }
        
        if (operationTask.getPlanStartTime() == null) {
            throw new BaseException("计划开始时间不能为空");
        }
        
        if (operationTask.getPlanEndTime() == null) {
            throw new BaseException("计划结束时间不能为空");
        }
        
        if (operationTask.getPlanQuantity() == null || operationTask.getPlanQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException("计划作业量必须大于0");
        }
        
        if (operationTask.getResponsibleUserId() == null) {
            throw new BaseException("负责人ID不能为空");
        }
        
        if (operationTask.getCreateUserId() == null) {
            throw new BaseException("创建人ID不能为空");
        }
        
        // 生成任务编码
        String taskCode = "TASK_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        operationTask.setTaskCode(taskCode);
        
        // 设置默认状态为待执行（1）
        if (operationTask.getStatus() == null) {
            operationTask.setStatus(1);
        }
        
        // 设置创建时间和更新时间
        operationTask.setCreateTime(LocalDateTime.now());
        operationTask.setUpdateTime(LocalDateTime.now());
        
        // 调用mapper的insert方法插入数据
        return operationTaskMapper.insert(operationTask) > 0;
    }

    /**
     * 更新作业调度任务
     * @param operationTask 作业调度任务信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateOperationTask(OperationTask operationTask) {
        // 进行业务验证
        if (operationTask == null) {
            throw new BaseException("作业调度任务信息不能为空");
        }
        
        if (operationTask.getId() == null) {
            throw new BaseException("任务ID不能为空");
        }
        
        // 检查任务是否存在
        OperationTask existingTask = operationTaskMapper.selectById(operationTask.getId());
        if (existingTask == null) {
            throw new BaseException("未找到ID为" + operationTask.getId() + "的作业调度任务");
        }
        
        // 状态为已完成或已取消的任务不允许修改
        if (existingTask.getStatus() != null && (existingTask.getStatus() == 3 || existingTask.getStatus() == 4)) {
            throw new BaseException("已完成或已取消的任务不允许修改");
        }
        
        // 设置更新时间
        operationTask.setUpdateTime(LocalDateTime.now());
        
        // 调用mapper的updateById方法更新数据
        return operationTaskMapper.updateById(operationTask) > 0;
    }

    /**
     * 删除作业调度任务
     * @param id 任务ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteOperationTask(Long id) {
        // 进行业务验证
        if (id == null) {
            throw new BaseException("任务ID不能为空");
        }
        
        // 检查任务是否存在
        OperationTask existingTask = operationTaskMapper.selectById(id);
        if (existingTask == null) {
            throw new BaseException("未找到ID为" + id + "的作业调度任务");
        }
        
        // 状态为执行中或已完成的任务不允许删除
        if (existingTask.getStatus() != null && (existingTask.getStatus() == 2 || existingTask.getStatus() == 3)) {
            throw new BaseException("执行中或已完成的任务不允许删除");
        }
        
        // 调用mapper的deleteById方法删除数据
        return operationTaskMapper.deleteById(id) > 0;
    }

    /**
     * 分页查询作业调度任务
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    @Override
    public Page<OperationTask> getOperationTaskPage(int pageNum, int pageSize, Map<String, Object> params) {
        // 创建分页对象
        Page<OperationTask> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<OperationTask> queryWrapper = new QueryWrapper<>();

        // 根据参数构建查询条件
        if (params != null) {
            // 任务编码
            if (params.containsKey("taskCode") && params.get("taskCode") != null) {
                queryWrapper.like("task_code", params.get("taskCode"));
            }
            // 农机ID
            if (params.containsKey("machineryId") && params.get("machineryId") != null) {
                queryWrapper.eq("machinery_id", params.get("machineryId"));
            }
            // 地块ID
            if (params.containsKey("farmlandId") && params.get("farmlandId") != null) {
                queryWrapper.eq("farmland_id", params.get("farmlandId"));
            }
            // 作业类型
            if (params.containsKey("operationType") && params.get("operationType") != null) {
                queryWrapper.eq("operation_type", params.get("operationType"));
            }
            // 状态
            if (params.containsKey("status") && params.get("status") != null) {
                queryWrapper.eq("status", params.get("status"));
            }
            // 负责人ID
            if (params.containsKey("responsibleUserId") && params.get("responsibleUserId") != null) {
                queryWrapper.eq("responsible_user_id", params.get("responsibleUserId"));
            }
            // 创建时间范围
            if (params.containsKey("startDate") && params.get("startDate") != null) {
                queryWrapper.ge("create_time", params.get("startDate"));
            }
            if (params.containsKey("endDate") && params.get("endDate") != null) {
                queryWrapper.le("create_time", params.get("endDate"));
            }
        }

        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");

        // 调用mapper的selectPage方法进行分页查询
        return operationTaskMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据农机ID查询作业调度任务
     * @param machineryId 农机ID
     * @return 作业调度任务列表
     */
    @Override
    public List<OperationTask> getOperationTasksByMachineryId(Long machineryId) {
        if (machineryId == null) {
            throw new BaseException("农机ID不能为空");
        }
        
        QueryWrapper<OperationTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("machinery_id", machineryId);
        queryWrapper.orderByDesc("create_time");
        
        return operationTaskMapper.selectList(queryWrapper);
    }

    /**
     * 根据地块ID查询作业调度任务
     * @param farmlandId 地块ID
     * @return 作业调度任务列表
     */
    @Override
    public List<OperationTask> getOperationTasksByFarmlandId(Long farmlandId) {
        if (farmlandId == null) {
            throw new BaseException("地块ID不能为空");
        }
        
        QueryWrapper<OperationTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("farmland_id", farmlandId);
        queryWrapper.orderByDesc("create_time");
        
        return operationTaskMapper.selectList(queryWrapper);
    }

    /**
     * 根据状态查询作业调度任务
     * @param status 状态：1-待执行，2-执行中，3-已完成，4-已取消
     * @return 作业调度任务列表
     */
    @Override
    public List<OperationTask> getOperationTasksByStatus(Integer status) {
        if (status == null) {
            throw new BaseException("状态不能为空");
        }
        
        QueryWrapper<OperationTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        queryWrapper.orderByDesc("create_time");
        
        return operationTaskMapper.selectList(queryWrapper);
    }
}