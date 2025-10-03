package org.agrimachinerymanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.agrimachinerymanager.entity.Notification;
import java.util.List;
import java.util.Map;

/**
 * 通知提醒服务接口
 */
public interface NotificationService {
    
    /**
     * 获取所有通知提醒
     * @return 通知提醒列表
     */
    List<Notification> getAllNotifications();
    
    /**
     * 根据ID获取通知提醒
     * @param id 通知ID
     * @return 通知提醒
     */
    Notification getNotificationById(Long id);
    
    /**
     * 新增通知提醒
     * @param notification 通知提醒信息
     * @return 是否新增成功
     */
    boolean addNotification(Notification notification);
    
    /**
     * 更新通知提醒
     * @param notification 通知提醒信息
     * @return 是否更新成功
     */
    boolean updateNotification(Notification notification);
    
    /**
     * 删除通知提醒
     * @param id 通知ID
     * @return 是否删除成功
     */
    boolean deleteNotification(Long id);
    
    /**
     * 分页查询通知提醒
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param params 查询参数
     * @return 分页结果
     */
    Page<Notification> getNotificationPage(int pageNum, int pageSize, Map<String, Object> params);
    
    /**
     * 根据用户ID获取通知提醒列表
     * @param userId 用户ID
     * @return 通知提醒列表
     */
    List<Notification> getNotificationsByUserId(Long userId);
    
    /**
     * 标记通知为已读
     * @param id 通知ID
     * @return 是否标记成功
     */
    boolean markNotificationAsRead(Long id);
}