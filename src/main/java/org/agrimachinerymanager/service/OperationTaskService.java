package org.agrimachinerymanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.OperationTask;

import java.util.List;
import java.util.Map;

/**
 * 作业调度任务服务接口
 */
public interface OperationTaskService {

    /**
     * 获取所有作业调度任务
     * @return 作业调度任务列表
     */
    List<OperationTask> getAllOperationTasks();

    /**
     * 根据ID获取作业调度任务
     * @param id 任务ID
     * @return 作业调度任务
     */
    OperationTask getOperationTaskById(Long id);

    /**
     * 新增作业调度任务
     * @param operationTask 作业调度任务信息
     * @return 是否新增成功
     */
    boolean addOperationTask(OperationTask operationTask);

    /**
     * 更新作业调度任务
     * @param operationTask 作业调度任务信息
     * @return 是否更新成功
     */
    boolean updateOperationTask(OperationTask operationTask);

    /**
     * 删除作业调度任务
     * @param id 任务ID
     * @return 是否删除成功
     */
    boolean deleteOperationTask(Long id);

    /**
     * 分页查询作业调度任务
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    Page<OperationTask> getOperationTaskPage(int pageNum, int pageSize, Map<String, Object> params);
    
    /**
     * 根据农机ID查询作业调度任务
     * @param machineryId 农机ID
     * @return 作业调度任务列表
     */
    List<OperationTask> getOperationTasksByMachineryId(Long machineryId);
    
    /**
     * 根据地块ID查询作业调度任务
     * @param farmlandId 地块ID
     * @return 作业调度任务列表
     */
    List<OperationTask> getOperationTasksByFarmlandId(Long farmlandId);
    
    /**
     * 根据状态查询作业调度任务
     * @param status 状态：1-待执行，2-执行中，3-已完成，4-已取消
     * @return 作业调度任务列表
     */
    List<OperationTask> getOperationTasksByStatus(Integer status);
}