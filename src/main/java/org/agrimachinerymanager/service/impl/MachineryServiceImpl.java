package org.agrimachinerymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.Machinery;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.mapper.MachineryMapper;
import org.agrimachinerymanager.service.MachineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 农机档案服务实现类
 */
@Service
public class MachineryServiceImpl implements MachineryService {
    
    @Autowired
    private MachineryMapper machineryMapper;
    
    /**
     * 获取所有农机档案
     * @return 农机档案列表
     */
    @Override
    public List<Machinery> getAllMachinery() {
        // 使用MyBatis-Plus的QueryWrapper构建查询条件
        // 这里不添加条件，表示查询所有记录
        QueryWrapper<Machinery> queryWrapper = new QueryWrapper<>();
        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");
        
        // 调用mapper的selectList方法查询所有农机档案
        return machineryMapper.selectList(queryWrapper);
    }
    
    /**
     * 根据ID获取农机档案
     * @param id 农机ID
     * @return 农机档案
     */
    @Override
    public Machinery getMachineryById(Long id) {
        Machinery machinery = machineryMapper.selectById(id);
        if (machinery == null) {
            throw new BaseException("未找到ID为" + id + "的农机档案");
        }
        return machinery;
    }
    
    /**
     * 新增农机档案
     * @param machinery 农机档案信息
     * @return 是否新增成功
     */
    @Override
    public boolean addMachinery(Machinery machinery) {
        // 进行业务验证
        if (machinery == null) {
            throw new BaseException("农机档案信息不能为空");
        }
        
        if (machinery.getMachineryCode() == null || machinery.getMachineryCode().trim().isEmpty()) {
            throw new BaseException("农机编号不能为空");
        }
        
        if (machinery.getBrand() == null || machinery.getBrand().trim().isEmpty()) {
            throw new BaseException("农机品牌不能为空");
        }
        
        // 检查农机编号是否已存在
        QueryWrapper<Machinery> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("machinery_code", machinery.getMachineryCode());
        Machinery existingMachinery = machineryMapper.selectOne(queryWrapper);
        if (existingMachinery != null) {
            throw new BaseException("农机编号已存在：" + machinery.getMachineryCode());
        }
        
        // 设置创建时间和更新时间
        machinery.setCreateTime(LocalDateTime.now());
        machinery.setUpdateTime(LocalDateTime.now());
        // 调用mapper的insert方法插入数据
        return machineryMapper.insert(machinery) > 0;
    }
    
    /**
     * 更新农机档案
     * @param machinery 农机档案信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateMachinery(Machinery machinery) {
        // 进行业务验证
        if (machinery == null) {
            throw new BaseException("农机档案信息不能为空");
        }
        
        if (machinery.getId() == null) {
            throw new BaseException("农机ID不能为空");
        }
        
        // 检查农机是否存在
        Machinery existingMachinery = machineryMapper.selectById(machinery.getId());
        if (existingMachinery == null) {
            throw new BaseException("未找到ID为" + machinery.getId() + "的农机档案");
        }
        
        // 检查更新的农机编号是否与其他农机冲突
        if (machinery.getMachineryCode() != null && !machinery.getMachineryCode().equals(existingMachinery.getMachineryCode())) {
            QueryWrapper<Machinery> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("machinery_code", machinery.getMachineryCode());
            queryWrapper.ne("id", machinery.getId());
            Machinery conflictMachinery = machineryMapper.selectOne(queryWrapper);
            if (conflictMachinery != null) {
                throw new BaseException("农机编号已被其他农机使用：" + machinery.getMachineryCode());
            }
        }
        
        // 设置更新时间
        machinery.setUpdateTime(LocalDateTime.now());
        // 调用mapper的updateById方法更新数据
        return machineryMapper.updateById(machinery) > 0;
    }
    
    /**
     * 删除农机档案
     * @param id 农机ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteMachinery(Long id) {
        // 进行业务验证
        if (id == null) {
            throw new BaseException("农机ID不能为空");
        }
        
        // 检查农机是否存在
        Machinery existingMachinery = machineryMapper.selectById(id);
        if (existingMachinery == null) {
            throw new BaseException("未找到ID为" + id + "的农机档案");
        }
        
        // 这里可以添加其他业务逻辑验证，比如判断农机是否有相关联的数据
        // 例如：检查该农机是否有关联的维护记录等
        // 如果有关联数据，可以抛出异常阻止删除
        
        // 调用mapper的deleteById方法删除数据
        return machineryMapper.deleteById(id) > 0;
    }
    
    /**
     * 分页查询农机档案
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    @Override
    public Page<Machinery> getMachineryPage(int pageNum, int pageSize, Map<String, Object> params) {
        // 创建分页对象
        Page<Machinery> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<Machinery> queryWrapper = new QueryWrapper<>();
        
        // 根据参数构建查询条件
        if (params != null) {
            // 农机编号
            if (params.containsKey("machineryCode") && params.get("machineryCode") != null) {
                queryWrapper.like("machinery_code", params.get("machineryCode"));
            }
            // 品牌
            if (params.containsKey("brand") && params.get("brand") != null) {
                queryWrapper.like("brand", params.get("brand"));
            }
            // 型号
            if (params.containsKey("model") && params.get("model") != null) {
                queryWrapper.like("model", params.get("model"));
            }
            // 状态
            if (params.containsKey("status") && params.get("status") != null) {
                queryWrapper.eq("status", params.get("status"));
            }
            // 部门
            if (params.containsKey("department") && params.get("department") != null) {
                queryWrapper.like("department", params.get("department"));
            }
        }
        
        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");
        
        // 调用mapper的selectPage方法进行分页查询
        return machineryMapper.selectPage(page, queryWrapper);
    }
}
