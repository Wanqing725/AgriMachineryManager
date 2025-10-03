package org.agrimachinerymanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.agrimachinerymanager.entity.SysDict;

import java.util.List;

/**
 * 数据字典数据访问接口
 */
public interface SysDictMapper extends BaseMapper<SysDict> {
    
    /**
     * 根据字典类型查询字典数据
     * @param type 字典类型
     * @return 字典数据列表
     */
    List<SysDict> selectByType(String type);
    
    /**
     * 根据多个字典类型查询字典数据
     * @param types 字典类型列表
     * @return 字典数据列表
     */
    List<SysDict> selectByTypes(List<String> types);
    
    /**
     * 根据字典类型和编码查询字典数据
     * @param type 字典类型
     * @param code 字典编码
     * @return 字典数据
     */
    SysDict selectByTypeAndCode(String type, String code);
}