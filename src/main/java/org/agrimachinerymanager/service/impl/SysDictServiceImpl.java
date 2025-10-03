package org.agrimachinerymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.SysDict;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.mapper.SysDictMapper;
import org.agrimachinerymanager.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 数据字典服务实现类
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     * 获取所有数据字典
     * @return 数据字典列表
     */
    @Override
    public List<SysDict> getAllSysDicts() {
        // 使用MyBatis-Plus的QueryWrapper构建查询条件
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        // 添加按类型和排序字段排序
        queryWrapper.orderByAsc("type", "sort");

        // 调用mapper的selectList方法查询所有数据字典
        return sysDictMapper.selectList(queryWrapper);
    }

    /**
     * 根据ID获取数据字典
     * @param id 字典ID
     * @return 数据字典
     */
    @Override
    public SysDict getSysDictById(Long id) {
        SysDict sysDict = sysDictMapper.selectById(id);
        if (sysDict == null) {
            throw new BaseException("未找到ID为" + id + "的数据字典信息");
        }
        return sysDict;
    }

    /**
     * 新增数据字典
     * @param sysDict 数据字典信息
     * @return 是否新增成功
     */
    @Override
    public boolean addSysDict(SysDict sysDict) {
        // 进行业务验证
        if (sysDict == null) {
            throw new BaseException("数据字典信息不能为空");
        }
        
        if (sysDict.getType() == null || sysDict.getType().trim().isEmpty()) {
            throw new BaseException("字典类型不能为空");
        }
        
        if (sysDict.getCode() == null || sysDict.getCode().trim().isEmpty()) {
            throw new BaseException("字典编码不能为空");
        }
        
        if (sysDict.getName() == null || sysDict.getName().trim().isEmpty()) {
            throw new BaseException("字典名称不能为空");
        }
        
        // 检查同一类型下字典编码是否已存在
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", sysDict.getType());
        queryWrapper.eq("code", sysDict.getCode());
        SysDict existingDict = sysDictMapper.selectOne(queryWrapper);
        if (existingDict != null) {
            throw new BaseException("字典类型[" + sysDict.getType() + "]下已存在编码为[" + sysDict.getCode() + "]的字典");
        }
        
        // 设置排序号默认值
        if (sysDict.getSort() == null) {
            sysDict.setSort(0);
        }
        
        // 设置创建时间
        sysDict.setCreateTime(LocalDateTime.now());
        
        // 调用mapper的insert方法插入数据
        return sysDictMapper.insert(sysDict) > 0;
    }

    /**
     * 更新数据字典
     * @param sysDict 数据字典信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateSysDict(SysDict sysDict) {
        // 进行业务验证
        if (sysDict == null) {
            throw new BaseException("数据字典信息不能为空");
        }
        
        if (sysDict.getId() == null) {
            throw new BaseException("字典ID不能为空");
        }
        
        if (sysDict.getType() == null || sysDict.getType().trim().isEmpty()) {
            throw new BaseException("字典类型不能为空");
        }
        
        if (sysDict.getCode() == null || sysDict.getCode().trim().isEmpty()) {
            throw new BaseException("字典编码不能为空");
        }
        
        if (sysDict.getName() == null || sysDict.getName().trim().isEmpty()) {
            throw new BaseException("字典名称不能为空");
        }
        
        // 检查字典是否存在
        SysDict existingDict = sysDictMapper.selectById(sysDict.getId());
        if (existingDict == null) {
            throw new BaseException("未找到ID为" + sysDict.getId() + "的数据字典");
        }
        
        // 检查更新后的字典编码是否与同类型下其他字典冲突
        if (!sysDict.getCode().equals(existingDict.getCode())) {
            QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", sysDict.getType());
            queryWrapper.eq("code", sysDict.getCode());
            queryWrapper.ne("id", sysDict.getId());
            SysDict conflictDict = sysDictMapper.selectOne(queryWrapper);
            if (conflictDict != null) {
                throw new BaseException("字典类型[" + sysDict.getType() + "]下编码[" + sysDict.getCode() + "]已被使用");
            }
        }
        
        // 设置排序号默认值
        if (sysDict.getSort() == null) {
            sysDict.setSort(0);
        }
        
        // 调用mapper的updateById方法更新数据
        return sysDictMapper.updateById(sysDict) > 0;
    }

    /**
     * 删除数据字典
     * @param id 字典ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteSysDict(Long id) {
        // 进行业务验证
        if (id == null) {
            throw new BaseException("字典ID不能为空");
        }
        
        // 检查字典是否存在
        SysDict existingDict = sysDictMapper.selectById(id);
        if (existingDict == null) {
            throw new BaseException("未找到ID为" + id + "的数据字典");
        }
        
        // 这里可以添加其他业务逻辑验证，比如判断字典是否被其他模块引用
        // 如果有引用，可以抛出异常阻止删除
        
        // 调用mapper的deleteById方法删除数据
        return sysDictMapper.deleteById(id) > 0;
    }

    /**
     * 分页查询数据字典
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    @Override
    public Page<SysDict> getSysDictPage(int pageNum, int pageSize, Map<String, Object> params) {
        // 创建分页对象
        Page<SysDict> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();

        // 根据参数构建查询条件
        if (params != null) {
            // 字典类型
            if (params.containsKey("type") && params.get("type") != null) {
                queryWrapper.eq("type", params.get("type"));
            }
            // 字典编码
            if (params.containsKey("code") && params.get("code") != null) {
                queryWrapper.like("code", params.get("code"));
            }
            // 字典名称
            if (params.containsKey("name") && params.get("name") != null) {
                queryWrapper.like("name", params.get("name"));
            }
        }

        // 添加按类型和排序字段排序
        queryWrapper.orderByAsc("type", "sort");

        // 调用mapper的selectPage方法进行分页查询
        return sysDictMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据字典类型获取字典数据
     * @param type 字典类型
     * @return 字典数据列表
     */
    @Override
    public List<SysDict> getSysDictByType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new BaseException("字典类型不能为空");
        }
        
        return sysDictMapper.selectByType(type);
    }

    /**
     * 根据多个字典类型获取字典数据
     * @param types 字典类型列表
     * @return 字典数据列表
     */
    @Override
    public List<SysDict> getSysDictByTypes(List<String> types) {
        if (types == null || types.isEmpty()) {
            throw new BaseException("字典类型列表不能为空");
        }
        
        return sysDictMapper.selectByTypes(types);
    }

    /**
     * 根据字典类型和编码获取字典数据
     * @param type 字典类型
     * @param code 字典编码
     * @return 字典数据
     */
    @Override
    public SysDict getSysDictByTypeAndCode(String type, String code) {
        if (type == null || type.trim().isEmpty()) {
            throw new BaseException("字典类型不能为空");
        }
        
        if (code == null || code.trim().isEmpty()) {
            throw new BaseException("字典编码不能为空");
        }
        
        return sysDictMapper.selectByTypeAndCode(type, code);
    }
}