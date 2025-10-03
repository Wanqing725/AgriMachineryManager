package org.agrimachinerymanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.SysDict;

import java.util.List;
import java.util.Map;

/**
 * 数据字典服务接口
 */
public interface SysDictService {

    /**
     * 获取所有数据字典
     * @return 数据字典列表
     */
    List<SysDict> getAllSysDicts();

    /**
     * 根据ID获取数据字典
     * @param id 字典ID
     * @return 数据字典
     */
    SysDict getSysDictById(Long id);

    /**
     * 新增数据字典
     * @param sysDict 数据字典信息
     * @return 是否新增成功
     */
    boolean addSysDict(SysDict sysDict);

    /**
     * 更新数据字典
     * @param sysDict 数据字典信息
     * @return 是否更新成功
     */
    boolean updateSysDict(SysDict sysDict);

    /**
     * 删除数据字典
     * @param id 字典ID
     * @return 是否删除成功
     */
    boolean deleteSysDict(Long id);

    /**
     * 分页查询数据字典
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    Page<SysDict> getSysDictPage(int pageNum, int pageSize, Map<String, Object> params);
    
    /**
     * 根据字典类型获取字典数据
     * @param type 字典类型
     * @return 字典数据列表
     */
    List<SysDict> getSysDictByType(String type);
    
    /**
     * 根据多个字典类型获取字典数据
     * @param types 字典类型列表
     * @return 字典数据列表
     */
    List<SysDict> getSysDictByTypes(List<String> types);
    
    /**
     * 根据字典类型和编码获取字典数据
     * @param type 字典类型
     * @param code 字典编码
     * @return 字典数据
     */
    SysDict getSysDictByTypeAndCode(String type, String code);
}