package org.agrimachinerymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.Farmland;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.mapper.FarmlandMapper;
import org.agrimachinerymanager.service.FarmlandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 地块信息服务实现类
 */
@Service
public class FarmlandServiceImpl implements FarmlandService {
    
    @Autowired
    private FarmlandMapper farmlandMapper;
    
    /**
     * 获取所有地块信息
     * @return 地块信息列表
     */
    @Override
    public List<Farmland> getAllFarmlands() {
        // 使用MyBatis-Plus的QueryWrapper构建查询条件
        // 这里不添加条件，表示查询所有记录
        QueryWrapper<Farmland> queryWrapper = new QueryWrapper<>();
        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");
        
        // 调用mapper的selectList方法查询所有地块信息
        return farmlandMapper.selectList(queryWrapper);
    }
    
    /**
     * 根据ID获取地块信息
     * @param id 地块ID
     * @return 地块信息
     */
    @Override
    public Farmland getFarmlandById(Long id) {
        Farmland farmland = farmlandMapper.selectById(id);
        if (farmland == null) {
            throw new BaseException("未找到ID为" + id + "的地块信息");
        }
        return farmland;
    }
    
    /**
     * 新增地块信息
     * @param farmland 地块信息
     * @return 是否新增成功
     */
    @Override
    public boolean addFarmland(Farmland farmland) {
        // 进行业务验证
        if (farmland == null) {
            throw new BaseException("地块信息不能为空");
        }
        
        if (farmland.getLandCode() == null || farmland.getLandCode().trim().isEmpty()) {
            throw new BaseException("地块编码不能为空");
        }
        
        if (farmland.getName() == null || farmland.getName().trim().isEmpty()) {
            throw new BaseException("地块名称不能为空");
        }
        
        // 检查地块编码是否已存在
        QueryWrapper<Farmland> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("land_code", farmland.getLandCode());
        Farmland existingFarmland = farmlandMapper.selectOne(queryWrapper);
        if (existingFarmland != null) {
            throw new BaseException("地块编码已存在：" + farmland.getLandCode());
        }
        
        // 设置创建时间和更新时间
        farmland.setCreateTime(LocalDateTime.now());
        farmland.setUpdateTime(LocalDateTime.now());
        
        // 调用mapper的insert方法插入数据
        return farmlandMapper.insert(farmland) > 0;
    }
    
    /**
     * 更新地块信息
     * @param farmland 地块信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateFarmland(Farmland farmland) {
        // 进行业务验证
        if (farmland == null) {
            throw new BaseException("地块信息不能为空");
        }
        
        if (farmland.getId() == null) {
            throw new BaseException("地块ID不能为空");
        }
        
        // 检查地块是否存在
        Farmland existingFarmland = farmlandMapper.selectById(farmland.getId());
        if (existingFarmland == null) {
            throw new BaseException("未找到ID为" + farmland.getId() + "的地块信息");
        }
        
        // 检查更新的地块编码是否与其他地块冲突
        if (farmland.getLandCode() != null && !farmland.getLandCode().equals(existingFarmland.getLandCode())) {
            QueryWrapper<Farmland> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("land_code", farmland.getLandCode());
            queryWrapper.ne("id", farmland.getId());
            Farmland conflictFarmland = farmlandMapper.selectOne(queryWrapper);
            if (conflictFarmland != null) {
                throw new BaseException("地块编码已被其他地块使用：" + farmland.getLandCode());
            }
        }
        
        // 设置更新时间
        farmland.setUpdateTime(LocalDateTime.now());
        // 调用mapper的updateById方法更新数据
        return farmlandMapper.updateById(farmland) > 0;
    }
    
    /**
     * 删除地块信息
     * @param id 地块ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteFarmland(Long id) {
        // 进行业务验证
        if (id == null) {
            throw new BaseException("地块ID不能为空");
        }
        
        // 检查地块是否存在
        Farmland existingFarmland = farmlandMapper.selectById(id);
        if (existingFarmland == null) {
            throw new BaseException("未找到ID为" + id + "的地块信息");
        }
        
        // 这里可以添加其他业务逻辑验证，比如判断地块是否有相关联的数据
        // 例如：检查该地块是否有关联的作业记录等
        // 如果有关联数据，可以抛出异常阻止删除
        
        // 调用mapper的deleteById方法删除数据
        return farmlandMapper.deleteById(id) > 0;
    }
    
    /**
     * 分页查询地块信息
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    @Override
    public Page<Farmland> getFarmlandPage(int pageNum, int pageSize, Map<String, Object> params) {
        // 创建分页对象
        Page<Farmland> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<Farmland> queryWrapper = new QueryWrapper<>();
        
        // 根据参数构建查询条件
        if (params != null) {
            // 地块编码
            if (params.containsKey("landCode") && params.get("landCode") != null) {
                queryWrapper.like("land_code", params.get("landCode"));
            }
            // 地块名称
            if (params.containsKey("name") && params.get("name") != null) {
                queryWrapper.like("name", params.get("name"));
            }
            // 位置
            if (params.containsKey("location") && params.get("location") != null) {
                queryWrapper.like("location", params.get("location"));
            }
        }
        
        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");
        
        // 调用mapper的selectPage方法进行分页查询
        return farmlandMapper.selectPage(page, queryWrapper);
    }
}