package org.agrimachinerymanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.Farmland;
import java.util.List;
import java.util.Map;

/**
 * 地块信息服务接口
 */
public interface FarmlandService {
    
    /**
     * 获取所有地块信息
     * @return 地块信息列表
     */
    List<Farmland> getAllFarmlands();
    
    /**
     * 根据ID获取地块信息
     * @param id 地块ID
     * @return 地块信息
     */
    Farmland getFarmlandById(Long id);
    
    /**
     * 新增地块信息
     * @param farmland 地块信息
     * @return 是否新增成功
     */
    boolean addFarmland(Farmland farmland);
    
    /**
     * 更新地块信息
     * @param farmland 地块信息
     * @return 是否更新成功
     */
    boolean updateFarmland(Farmland farmland);
    
    /**
     * 删除地块信息
     * @param id 地块ID
     * @return 是否删除成功
     */
    boolean deleteFarmland(Long id);
    
    /**
     * 分页查询地块信息
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    Page<Farmland> getFarmlandPage(int pageNum, int pageSize, Map<String, Object> params);
}