package org.agrimachinerymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.SysUser;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.mapper.SysUserMapper;
import org.agrimachinerymanager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 系统用户服务实现类
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 获取所有系统用户
     * @return 用户列表
     */
    @Override
    public List<SysUser> getAllSysUsers() {
        // 使用MyBatis-Plus的QueryWrapper构建查询条件
        // 这里不添加条件，表示查询所有记录
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");

        // 调用mapper的selectList方法查询所有用户
        return sysUserMapper.selectList(queryWrapper);
    }

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public SysUser getSysUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            throw new BaseException("未找到ID为" + id + "的用户信息");
        }
        return sysUser;
    }

    /**
     * 新增用户
     * @param sysUser 用户信息
     * @return 是否新增成功
     */
    @Override
    public boolean addSysUser(SysUser sysUser) {
        // 进行业务验证
        if (sysUser == null) {
            throw new BaseException("用户信息不能为空");
        }
        
        if (sysUser.getUsername() == null || sysUser.getUsername().trim().isEmpty()) {
            throw new BaseException("用户名不能为空");
        }
        
        if (sysUser.getPassword() == null || sysUser.getPassword().trim().isEmpty()) {
            throw new BaseException("密码不能为空");
        }
        
        // 检查用户名是否已存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", sysUser.getUsername());
        SysUser existingUser = sysUserMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            throw new BaseException("用户名已存在：" + sysUser.getUsername());
        }
        
        // 设置创建时间和更新时间
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        // 调用mapper的insert方法插入数据
        return sysUserMapper.insert(sysUser) > 0;
    }
    
    /**
     * 更新用户
     * @param sysUser 用户信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateSysUser(SysUser sysUser) {
        // 进行业务验证
        if (sysUser == null) {
            throw new BaseException("用户信息不能为空");
        }
        
        if (sysUser.getId() == null) {
            throw new BaseException("用户ID不能为空");
        }
        
        // 检查用户是否存在
        SysUser existingUser = sysUserMapper.selectById(sysUser.getId());
        if (existingUser == null) {
            throw new BaseException("未找到ID为" + sysUser.getId() + "的用户");
        }
        
        // 检查更新的用户名是否与其他用户冲突
        if (sysUser.getUsername() != null && !sysUser.getUsername().equals(existingUser.getUsername())) {
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", sysUser.getUsername());
            queryWrapper.ne("id", sysUser.getId());
            SysUser conflictUser = sysUserMapper.selectOne(queryWrapper);
            if (conflictUser != null) {
                throw new BaseException("用户名已被其他用户使用：" + sysUser.getUsername());
            }
        }
        
        // 设置更新时间
        sysUser.setUpdateTime(LocalDateTime.now());
        // 调用mapper的updateById方法更新数据
        return sysUserMapper.updateById(sysUser) > 0;
    }
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteSysUser(Long id) {
        // 进行业务验证
        if (id == null) {
            throw new BaseException("用户ID不能为空");
        }
        
        // 检查用户是否存在
        SysUser existingUser = sysUserMapper.selectById(id);
        if (existingUser == null) {
            throw new BaseException("未找到ID为" + id + "的用户");
        }
        
        // 这里可以添加其他业务逻辑验证，比如判断用户是否有相关联的数据
        // 例如：检查该用户是否有关联的操作记录等
        // 如果有关联数据，可以抛出异常阻止删除
        
        // 调用mapper的deleteById方法删除数据
        return sysUserMapper.deleteById(id) > 0;
    }

    /**
     * 分页查询用户
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    @Override
    public Page<SysUser> getSysUserPage(int pageNum, int pageSize, Map<String, Object> params) {
        // 创建分页对象
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();

        // 根据参数构建查询条件
        if (params != null) {
            // 用户名
            if (params.containsKey("username") && params.get("username") != null) {
                queryWrapper.like("username", params.get("username"));
            }
            // 真实姓名
            if (params.containsKey("realName") && params.get("realName") != null) {
                queryWrapper.like("real_name", params.get("realName"));
            }
            // 联系电话
            if (params.containsKey("phone") && params.get("phone") != null) {
                queryWrapper.like("phone", params.get("phone"));
            }
            // 角色
            if (params.containsKey("role") && params.get("role") != null) {
                queryWrapper.eq("role", params.get("role"));
            }
            // 状态
            if (params.containsKey("status") && params.get("status") != null) {
                queryWrapper.eq("status", params.get("status"));
            }
        }

        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");

        // 调用mapper的selectPage方法进行分页查询
        return sysUserMapper.selectPage(page, queryWrapper);
    }
}