package org.agrimachinerymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.Notification;
import org.agrimachinerymanager.exception.BaseException;
import org.agrimachinerymanager.mapper.NotificationMapper;
import org.agrimachinerymanager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 通知提醒服务实现类
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    
    @Autowired
    private NotificationMapper notificationMapper;
    
    /**
     * 获取所有通知提醒
     * @return 通知提醒列表
     */
    @Override
    public List<Notification> getAllNotifications() {
        // 使用MyBatis-Plus的QueryWrapper构建查询条件
        // 这里不添加条件，表示查询所有记录
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");
        
        // 调用mapper的selectList方法查询所有通知提醒
        return notificationMapper.selectList(queryWrapper);
    }
    
    /**
     * 根据ID获取通知提醒
     * @param id 通知ID
     * @return 通知提醒
     */
    @Override
    public Notification getNotificationById(Long id) {
        Notification notification = notificationMapper.selectById(id);
        if (notification == null) {
            throw new BaseException("未找到ID为" + id + "的通知提醒");
        }
        return notification;
    }
    
    /**
     * 新增通知提醒
     * @param notification 通知提醒信息
     * @return 是否新增成功
     */
    @Override
    public boolean addNotification(Notification notification) {
        // 进行业务验证
        if (notification == null) {
            throw new BaseException("通知提醒信息不能为空");
        }
        
        if (notification.getTitle() == null || notification.getTitle().trim().isEmpty()) {
            throw new BaseException("通知标题不能为空");
        }
        
        if (notification.getContent() == null || notification.getContent().trim().isEmpty()) {
            throw new BaseException("通知内容不能为空");
        }
        
        if (notification.getUserId() == null) {
            throw new BaseException("接收人ID不能为空");
        }
        
        // 设置创建时间和默认状态
        notification.setCreateTime(LocalDateTime.now());
        notification.setIsRead(0); // 默认为未读
        
        // 调用mapper的insert方法插入数据
        return notificationMapper.insert(notification) > 0;
    }
    
    /**
     * 更新通知提醒
     * @param notification 通知提醒信息
     * @return 是否更新成功
     */
    @Override
    public boolean updateNotification(Notification notification) {
        // 进行业务验证
        if (notification == null) {
            throw new BaseException("通知提醒信息不能为空");
        }
        
        if (notification.getId() == null) {
            throw new BaseException("通知ID不能为空");
        }
        
        // 检查通知是否存在
        Notification existingNotification = notificationMapper.selectById(notification.getId());
        if (existingNotification == null) {
            throw new BaseException("未找到ID为" + notification.getId() + "的通知提醒");
        }
        
        // 设置更新时间
        notification.setCreateTime(existingNotification.getCreateTime()); // 保持创建时间不变
        
        // 调用mapper的updateById方法更新数据
        return notificationMapper.updateById(notification) > 0;
    }
    
    /**
     * 删除通知提醒
     * @param id 通知ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteNotification(Long id) {
        // 进行业务验证
        if (id == null) {
            throw new BaseException("通知ID不能为空");
        }
        
        // 检查通知是否存在
        Notification existingNotification = notificationMapper.selectById(id);
        if (existingNotification == null) {
            throw new BaseException("未找到ID为" + id + "的通知提醒");
        }
        
        // 调用mapper的deleteById方法删除数据
        return notificationMapper.deleteById(id) > 0;
    }
    
    /**
     * 分页查询通知提醒
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    @Override
    public Page<Notification> getNotificationPage(int pageNum, int pageSize, Map<String, Object> params) {
        // 创建分页对象
        Page<Notification> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        
        // 根据参数构建查询条件
        if (params != null) {
            // 用户ID
            if (params.containsKey("userId") && params.get("userId") != null) {
                queryWrapper.eq("user_id", params.get("userId"));
            }
            // 已读状态
            if (params.containsKey("isRead") && params.get("isRead") != null) {
                queryWrapper.eq("is_read", params.get("isRead"));
            }
            // 关联模块
            if (params.containsKey("relatedModule") && params.get("relatedModule") != null) {
                queryWrapper.eq("related_module", params.get("relatedModule"));
            }
            // 关联ID
            if (params.containsKey("relatedId") && params.get("relatedId") != null) {
                queryWrapper.eq("related_id", params.get("relatedId"));
            }
        }
        
        // 添加按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");
        
        // 调用mapper的selectPage方法进行分页查询
        return notificationMapper.selectPage(page, queryWrapper);
    }
    
    /**
     * 根据用户ID获取通知提醒列表
     * @param userId 用户ID
     * @return 通知提醒列表
     */
    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        if (userId == null) {
            throw new BaseException("用户ID不能为空");
        }
        
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("create_time");
        
        return notificationMapper.selectList(queryWrapper);
    }
    
    /**
     * 标记通知为已读
     * @param id 通知ID
     * @return 是否标记成功
     */
    @Override
    public boolean markNotificationAsRead(Long id) {
        if (id == null) {
            throw new BaseException("通知ID不能为空");
        }
        
        // 检查通知是否存在
        Notification existingNotification = notificationMapper.selectById(id);
        if (existingNotification == null) {
            throw new BaseException("未找到ID为" + id + "的通知提醒");
        }
        
        // 更新已读状态
        existingNotification.setIsRead(1);
        
        return notificationMapper.updateById(existingNotification) > 0;
    }
}