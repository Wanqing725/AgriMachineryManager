package org.agrimachinerymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.MaintainRecord;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.mapper.MaintainRecordMapper;
import org.agrimachinerymanager.service.MaintainRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 农机维护记录服务实现类
 */
@Service
public class MaintainRecordServiceImpl implements MaintainRecordService {

    @Autowired
    private MaintainRecordMapper maintainRecordMapper;

    /**
     * 获取所有农机维护记录
     * @return 农机维护记录列表
     */
    @Override
    public List<MaintainRecord> getAllMaintainRecords() {
        // 使用MyBatis-Plus的QueryWrapper构建查询条件
        QueryWrapper<MaintainRecord> queryWrapper = new QueryWrapper<>();
        // 添加按维护时间倒序排序
        queryWrapper.orderByDesc("maintain_time");

        // 调用mapper的selectList方法查询所有农机维护记录
        return maintainRecordMapper.selectList(queryWrapper);
    }

    /**
     * 根据ID获取农机维护记录
     * @param id 记录ID
     * @return 农机维护记录
     */
    @Override
    public MaintainRecord getMaintainRecordById(Long id) {
        MaintainRecord maintainRecord = maintainRecordMapper.selectById(id);
        if (maintainRecord == null) {
            throw new BaseException("农机维护记录不存在");
        }
        return maintainRecord;
    }

    /**
     * 新增农机维护记录
     * @param maintainRecord 农机维护记录
     * @return 是否新增成功
     */
    @Override
    public boolean addMaintainRecord(MaintainRecord maintainRecord) {
        // 验证必要参数
        if (maintainRecord == null) {
            throw new BaseException("农机维护记录信息不能为空");
        }
        if (maintainRecord.getMachineryId() == null) {
            throw new BaseException("农机ID不能为空");
        }
        if (maintainRecord.getMaintainType() == null || maintainRecord.getMaintainType().isEmpty()) {
            throw new BaseException("维护类型不能为空");
        }
        if (maintainRecord.getMaintainTime() == null) {
            throw new BaseException("维护时间不能为空");
        }
        if (maintainRecord.getMaintainer() == null || maintainRecord.getMaintainer().isEmpty()) {
            throw new BaseException("维护人员不能为空");
        }
        if (maintainRecord.getCreateUserId() == null) {
            throw new BaseException("创建人ID不能为空");
        }
        if (maintainRecord.getDescription() == null || maintainRecord.getDescription().isEmpty()) {
            throw new BaseException("维护描述不能为空");
        }

        // 设置时间戳
        maintainRecord.setCreateTime(LocalDateTime.now());
        maintainRecord.setUpdateTime(LocalDateTime.now());

        // 调用mapper的insert方法添加农机维护记录
        return maintainRecordMapper.insert(maintainRecord) > 0;
    }

    /**
     * 更新农机维护记录
     * @param maintainRecord 农机维护记录
     * @return 是否更新成功
     */
    @Override
    public boolean updateMaintainRecord(MaintainRecord maintainRecord) {
        // 验证必要参数
        if (maintainRecord == null) {
            throw new BaseException("农机维护记录信息不能为空");
        }
        
        if (maintainRecord.getId() == null) {
            throw new BaseException("记录ID不能为空");
        }
        
        // 检查记录是否存在
        MaintainRecord existingRecord = maintainRecordMapper.selectById(maintainRecord.getId());
        if (existingRecord == null) {
            throw new BaseException("农机维护记录不存在");
        }

        // 更新时间戳
        maintainRecord.setUpdateTime(LocalDateTime.now());

        // 调用mapper的updateById方法更新农机维护记录
        return maintainRecordMapper.updateById(maintainRecord) > 0;
    }

    /**
     * 删除农机维护记录
     * @param id 记录ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteMaintainRecord(Long id) {
        // 验证必要参数
        if (id == null) {
            throw new BaseException("记录ID不能为空");
        }

        // 检查记录是否存在
        MaintainRecord existingRecord = maintainRecordMapper.selectById(id);
        if (existingRecord == null) {
            throw new BaseException("农机维护记录不存在");
        }

        // 调用mapper的deleteById方法删除农机维护记录
        return maintainRecordMapper.deleteById(id) > 0;
    }

    /**
     * 分页查询农机维护记录
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    @Override
    public Page<MaintainRecord> getMaintainRecordPage(int pageNum, int pageSize, Map<String, Object> params) {
        // 创建分页对象
        Page<MaintainRecord> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<MaintainRecord> queryWrapper = new QueryWrapper<>();

        // 根据参数构建查询条件
        if (params != null) {
            // 农机ID
            if (params.containsKey("machineryId") && params.get("machineryId") != null) {
                queryWrapper.eq("machinery_id", params.get("machineryId"));
            }
            // 维护类型
            if (params.containsKey("maintainType") && params.get("maintainType") != null) {
                queryWrapper.eq("maintain_type", params.get("maintainType"));
            }
            // 维护人员
            if (params.containsKey("maintainer") && params.get("maintainer") != null) {
                queryWrapper.like("maintainer", params.get("maintainer"));
            }
            // 创建人ID
            if (params.containsKey("createUserId") && params.get("createUserId") != null) {
                queryWrapper.eq("create_user_id", params.get("createUserId"));
            }
            // 维护描述（模糊查询）
            if (params.containsKey("description") && params.get("description") != null) {
                queryWrapper.like("description", params.get("description"));
            }
            // 维护时间范围
            if (params.containsKey("startTime") && params.get("startTime") != null) {
                queryWrapper.ge("maintain_time", params.get("startTime"));
            }
            if (params.containsKey("endTime") && params.get("endTime") != null) {
                queryWrapper.le("maintain_time", params.get("endTime"));
            }
            // 费用范围
            if (params.containsKey("minCost") && params.get("minCost") != null) {
                queryWrapper.ge("cost", params.get("minCost"));
            }
            if (params.containsKey("maxCost") && params.get("maxCost") != null) {
                queryWrapper.le("cost", params.get("maxCost"));
            }
        }

        // 添加按维护时间倒序排序
        queryWrapper.orderByDesc("maintain_time");

        // 调用mapper的selectPage方法进行分页查询
        return maintainRecordMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据农机ID查询维护记录
     * @param machineryId 农机ID
     * @return 维护记录列表
     */
    @Override
    public List<MaintainRecord> getMaintainRecordsByMachineryId(Long machineryId) {
        if (machineryId == null) {
            throw new BaseException("农机ID不能为空");
        }
        
        QueryWrapper<MaintainRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("machinery_id", machineryId);
        queryWrapper.orderByDesc("maintain_time");
        
        return maintainRecordMapper.selectList(queryWrapper);
    }

    /**
     * 根据维护类型查询维护记录
     * @param maintainType 维护类型
     * @return 维护记录列表
     */
    @Override
    public List<MaintainRecord> getMaintainRecordsByType(String maintainType) {
        if (maintainType == null || maintainType.isEmpty()) {
            throw new BaseException("维护类型不能为空");
        }
        
        QueryWrapper<MaintainRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("maintain_type", maintainType);
        queryWrapper.orderByDesc("maintain_time");
        
        return maintainRecordMapper.selectList(queryWrapper);
    }

    /**
     * 根据创建人ID查询维护记录
     * @param createUserId 创建人ID
     * @return 维护记录列表
     */
    @Override
    public List<MaintainRecord> getMaintainRecordsByCreateUserId(Long createUserId) {
        if (createUserId == null) {
            throw new BaseException("创建人ID不能为空");
        }
        
        QueryWrapper<MaintainRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_user_id", createUserId);
        queryWrapper.orderByDesc("maintain_time");
        
        return maintainRecordMapper.selectList(queryWrapper);
    }

    /**
     * 根据维护时间范围查询维护记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 维护记录列表
     */
    @Override
    public List<MaintainRecord> getMaintainRecordsByTimeRange(String startTime, String endTime) {
        if (startTime == null || startTime.isEmpty()) {
            throw new BaseException("开始时间不能为空");
        }
        if (endTime == null || endTime.isEmpty()) {
            throw new BaseException("结束时间不能为空");
        }
        
        QueryWrapper<MaintainRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("maintain_time", startTime);
        queryWrapper.le("maintain_time", endTime);
        queryWrapper.orderByDesc("maintain_time");
        
        return maintainRecordMapper.selectList(queryWrapper);
    }
}