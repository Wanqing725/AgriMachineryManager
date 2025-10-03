package org.agrimachinerymanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.agrimachinerymanager.entity.SysOperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统操作日志Mapper接口
 */
@Mapper
public interface SysOperateLogMapper extends BaseMapper<SysOperateLog> {
    
    // 可以在这里添加自定义的SQL查询方法
}