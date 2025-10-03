package org.agrimachinerymanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 系统用户服务接口
 */
public interface SysUserService {

    /**
     * 获取所有系统用户
     * @return 用户列表
     */
    List<SysUser> getAllSysUsers();

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    SysUser getSysUserById(Long id);

    /**
     * 新增用户
     * @param sysUser 用户信息
     * @return 是否新增成功
     */
    boolean addSysUser(SysUser sysUser);

    /**
     * 更新用户
     * @param sysUser 用户信息
     * @return 是否更新成功
     */
    boolean updateSysUser(SysUser sysUser);

    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否删除成功
     */
    boolean deleteSysUser(Long id);

    /**
     * 分页查询用户
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    Page<SysUser> getSysUserPage(int pageNum, int pageSize, Map<String, Object> params);
}