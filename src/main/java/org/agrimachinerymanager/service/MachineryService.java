package org.agrimachinerymanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.Machinery;
import java.util.List;
import java.util.Map;

/**
 * 农机档案服务接口
 */
public interface MachineryService {
    
    /**
     * 获取所有农机档案
     * @return 农机档案列表
     */
    List<Machinery> getAllMachinery();
    
    /**
     * 根据ID获取农机档案
     * @param id 农机ID
     * @return 农机档案
     */
    Machinery getMachineryById(Long id);
    
    /**
     * 新增农机档案
     * @param machinery 农机档案信息
     * @return 是否新增成功
     */
    boolean addMachinery(Machinery machinery);
    
    /**
     * 更新农机档案
     * @param machinery 农机档案信息
     * @return 是否更新成功
     */
    boolean updateMachinery(Machinery machinery);
    
    /**
     * 删除农机档案
     * @param id 农机ID
     * @return 是否删除成功
     */
    boolean deleteMachinery(Long id);
    
    /**
     * 分页查询农机档案
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    Page<Machinery> getMachineryPage(int pageNum, int pageSize, Map<String, Object> params);
}
