package org.agrimachinerymanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.SysOperateLog;

import java.util.List;
import java.util.Map;

/**
 * 系统操作日志服务接口
 */
public interface SysOperateLogService {

    /**
     * 获取所有系统操作日志
     * @return 系统操作日志列表
     */
    List<SysOperateLog> getAllSysOperateLogs();

    /**
     * 根据ID获取系统操作日志
     * @param id 日志ID
     * @return 系统操作日志
     */
    SysOperateLog getSysOperateLogById(Long id);

    /**
     * 添加系统操作日志
     * @param sysOperateLog 系统操作日志
     */
    void addSysOperateLog(SysOperateLog sysOperateLog);

    /**
     * 更新系统操作日志
     * @param sysOperateLog 系统操作日志
     * @return 是否更新成功
     */
    boolean updateSysOperateLog(SysOperateLog sysOperateLog);

    /**
     * 删除系统操作日志
     * @param id 日志ID
     * @return 是否删除成功
     */
    boolean deleteSysOperateLog(Long id);

    /**
     * 分页查询系统操作日志
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    Page<SysOperateLog> getSysOperateLogPage(Integer pageNum, Integer pageSize, Map<String, Object> params);

    /**
     * 根据用户ID查询系统操作日志
     * @param userId 用户ID
     * @return 系统操作日志列表
     */
    List<SysOperateLog> getSysOperateLogsByUserId(Long userId);

    /**
     * 根据操作类型查询系统操作日志
     * @param operateType 操作类型
     * @return 系统操作日志列表
     */
    List<SysOperateLog> getSysOperateLogsByType(String operateType);

    /**
     * 根据操作模块查询系统操作日志
     * @param operateModule 操作模块
     * @return 系统操作日志列表
     */
    List<SysOperateLog> getSysOperateLogsByModule(String operateModule);
}