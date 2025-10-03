package org.agrimachinerymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.SysOperateLog;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.mapper.SysOperateLogMapper;
import org.agrimachinerymanager.service.SysOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 系统操作日志服务实现类
 */
@Service
public class SysOperateLogServiceImpl implements SysOperateLogService {

    @Autowired
    private SysOperateLogMapper sysOperateLogMapper;

    /**
     * 获取所有系统操作日志
     * @return 系统操作日志列表
     */
    @Override
    public List<SysOperateLog> getAllSysOperateLogs() {
        // 使用MyBatis-Plus的QueryWrapper构建查询条件
        QueryWrapper<SysOperateLog> queryWrapper = new QueryWrapper<>();
        // 添加按操作时间倒序排序
        queryWrapper.orderByDesc("operate_time");

        // 调用mapper的selectList方法查询所有系统操作日志
        return sysOperateLogMapper.selectList(queryWrapper);
    }

    /**
     * 根据ID获取系统操作日志
     * @param id 日志ID
     * @return 系统操作日志
     */
    @Override
    public SysOperateLog getSysOperateLogById(Long id) {
        SysOperateLog sysOperateLog = sysOperateLogMapper.selectById(id);
        if (sysOperateLog == null) {
            throw new BaseException("系统操作日志不存在");
        }
        return sysOperateLog;
    }

    /**
     * 添加系统操作日志
     * @param sysOperateLog 系统操作日志
     */
    @Override
    public void addSysOperateLog(SysOperateLog sysOperateLog) {
        // 验证必要参数
        if (sysOperateLog.getUserId() == null) {
            throw new BaseException("操作人ID不能为空");
        }
        if (sysOperateLog.getOperateType() == null || sysOperateLog.getOperateType().isEmpty()) {
            throw new BaseException("操作类型不能为空");
        }
        if (sysOperateLog.getOperateModule() == null || sysOperateLog.getOperateModule().isEmpty()) {
            throw new BaseException("操作模块不能为空");
        }
        if (sysOperateLog.getOperateContent() == null || sysOperateLog.getOperateContent().isEmpty()) {
            throw new BaseException("操作内容不能为空");
        }
        if (sysOperateLog.getOperateIp() == null || sysOperateLog.getOperateIp().isEmpty()) {
            throw new BaseException("操作IP不能为空");
        }

        // 设置操作时间
        if (sysOperateLog.getOperateTime() == null) {
            sysOperateLog.setOperateTime(LocalDateTime.now());
        }

        // 调用mapper的insert方法添加系统操作日志
        sysOperateLogMapper.insert(sysOperateLog);
    }

    /**
     * 更新系统操作日志
     * @param sysOperateLog 系统操作日志
     * @return 是否更新成功
     */
    @Override
    public boolean updateSysOperateLog(SysOperateLog sysOperateLog) {
        // 验证必要参数
        if (sysOperateLog.getId() == null) {
            throw new BaseException("日志ID不能为空");
        }
        
        // 检查日志是否存在
        SysOperateLog existingLog = sysOperateLogMapper.selectById(sysOperateLog.getId());
        if (existingLog == null) {
            throw new BaseException("系统操作日志不存在");
        }

        // 调用mapper的updateById方法更新系统操作日志
        int result = sysOperateLogMapper.updateById(sysOperateLog);
        return result > 0;
    }

    /**
     * 删除系统操作日志
     * @param id 日志ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteSysOperateLog(Long id) {
        // 验证必要参数
        if (id == null) {
            throw new BaseException("日志ID不能为空");
        }

        // 检查日志是否存在
        SysOperateLog existingLog = sysOperateLogMapper.selectById(id);
        if (existingLog == null) {
            throw new BaseException("系统操作日志不存在");
        }

        // 调用mapper的deleteById方法删除系统操作日志
        int result = sysOperateLogMapper.deleteById(id);
        return result > 0;
    }

    /**
     * 分页查询系统操作日志
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    @Override
    public Page<SysOperateLog> getSysOperateLogPage(Integer pageNum, Integer pageSize, Map<String, Object> params) {
        // 创建分页对象
        Page<SysOperateLog> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<SysOperateLog> queryWrapper = new QueryWrapper<>();

        // 根据参数构建查询条件
        if (params != null) {
            // 用户ID
            if (params.containsKey("userId") && params.get("userId") != null) {
                queryWrapper.eq("user_id", params.get("userId"));
            }
            // 操作类型
            if (params.containsKey("operateType") && params.get("operateType") != null) {
                queryWrapper.eq("operate_type", params.get("operateType"));
            }
            // 操作模块
            if (params.containsKey("operateModule") && params.get("operateModule") != null) {
                queryWrapper.eq("operate_module", params.get("operateModule"));
            }
            // 操作内容（模糊查询）
            if (params.containsKey("operateContent") && params.get("operateContent") != null) {
                queryWrapper.like("operate_content", params.get("operateContent"));
            }
            // 操作IP
            if (params.containsKey("operateIp") && params.get("operateIp") != null) {
                queryWrapper.eq("operate_ip", params.get("operateIp"));
            }
            // 操作时间范围
            if (params.containsKey("startTime") && params.get("startTime") != null) {
                queryWrapper.ge("operate_time", params.get("startTime"));
            }
            if (params.containsKey("endTime") && params.get("endTime") != null) {
                queryWrapper.le("operate_time", params.get("endTime"));
            }
        }

        // 添加按操作时间倒序排序
        queryWrapper.orderByDesc("operate_time");

        // 调用mapper的selectPage方法进行分页查询
        return sysOperateLogMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据用户ID查询系统操作日志
     * @param userId 用户ID
     * @return 系统操作日志列表
     */
    @Override
    public List<SysOperateLog> getSysOperateLogsByUserId(Long userId) {
        if (userId == null) {
            throw new BaseException("用户ID不能为空");
        }
        
        QueryWrapper<SysOperateLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("operate_time");
        
        return sysOperateLogMapper.selectList(queryWrapper);
    }

    /**
     * 根据操作类型查询系统操作日志
     * @param operateType 操作类型
     * @return 系统操作日志列表
     */
    @Override
    public List<SysOperateLog> getSysOperateLogsByType(String operateType) {
        if (operateType == null || operateType.isEmpty()) {
            throw new BaseException("操作类型不能为空");
        }
        
        QueryWrapper<SysOperateLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("operate_type", operateType);
        queryWrapper.orderByDesc("operate_time");
        
        return sysOperateLogMapper.selectList(queryWrapper);
    }

    /**
     * 根据操作模块查询系统操作日志
     * @param operateModule 操作模块
     * @return 系统操作日志列表
     */
    @Override
    public List<SysOperateLog> getSysOperateLogsByModule(String operateModule) {
        if (operateModule == null || operateModule.isEmpty()) {
            throw new BaseException("操作模块不能为空");
        }
        
        QueryWrapper<SysOperateLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("operate_module", operateModule);
        queryWrapper.orderByDesc("operate_time");
        
        return sysOperateLogMapper.selectList(queryWrapper);
    }
}