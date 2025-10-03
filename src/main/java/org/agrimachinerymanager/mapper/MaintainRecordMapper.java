package org.agrimachinerymanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.agrimachinerymanager.entity.MaintainRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 农机维护记录Mapper接口
 */
@Mapper
public interface MaintainRecordMapper extends BaseMapper<MaintainRecord> {
    
    // 可以在这里添加自定义的SQL查询方法
}