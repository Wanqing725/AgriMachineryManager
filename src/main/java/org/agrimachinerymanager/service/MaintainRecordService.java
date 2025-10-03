package org.agrimachinerymanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.MaintainRecord;

import java.util.List;
import java.util.Map;

/**
 * 农机维护记录服务接口
 */
public interface MaintainRecordService {

    /**
     * 获取所有农机维护记录
     * @return 农机维护记录列表
     */
    List<MaintainRecord> getAllMaintainRecords();

    /**
     * 根据ID获取农机维护记录
     * @param id 记录ID
     * @return 农机维护记录
     */
    MaintainRecord getMaintainRecordById(Long id);

    /**
     * 新增农机维护记录
     * @param maintainRecord 农机维护记录信息
     * @return 是否新增成功
     */
    boolean addMaintainRecord(MaintainRecord maintainRecord);

    /**
     * 更新农机维护记录
     * @param maintainRecord 农机维护记录信息
     * @return 是否更新成功
     */
    boolean updateMaintainRecord(MaintainRecord maintainRecord);

    /**
     * 删除农机维护记录
     * @param id 记录ID
     * @return 是否删除成功
     */
    boolean deleteMaintainRecord(Long id);

    /**
     * 分页查询农机维护记录
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    Page<MaintainRecord> getMaintainRecordPage(int pageNum, int pageSize, Map<String, Object> params);

    /**
     * 根据农机ID查询维护记录
     * @param machineryId 农机ID
     * @return 维护记录列表
     */
    List<MaintainRecord> getMaintainRecordsByMachineryId(Long machineryId);

    /**
     * 根据维护类型查询维护记录
     * @param maintainType 维护类型
     * @return 维护记录列表
     */
    List<MaintainRecord> getMaintainRecordsByType(String maintainType);

    /**
     * 根据创建人ID查询维护记录
     * @param createUserId 创建人ID
     * @return 维护记录列表
     */
    List<MaintainRecord> getMaintainRecordsByCreateUserId(Long createUserId);

    /**
     * 根据维护时间范围查询维护记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 维护记录列表
     */
    List<MaintainRecord> getMaintainRecordsByTimeRange(String startTime, String endTime);
}