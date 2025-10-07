package org.agrimachinerymanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.agrimachinerymanager.entity.Notification;
import org.agrimachinerymanager.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知提醒控制器
 */
@RestController
@RequestMapping("/notification")
@Tag(name = "通知提醒管理", description = "通知提醒的增删改查操作")
public class NotificationController {
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * 获取所有通知提醒
     * @return 通知提醒列表
     */
    @GetMapping("/getAllNotifications")
    @Operation(summary = "获取所有通知提醒", description = "查询系统中所有的通知提醒信息")
    public ApiResponse<List<Notification>> getAllNotifications() {
        // 调用service层方法获取所有通知提醒
        List<Notification> notificationList = notificationService.getAllNotifications();
        return ApiResponse.success(notificationList);
    }
    
    /**
     * 根据ID获取通知提醒
     * @param id 通知ID
     * @return 通知提醒
     */
    @GetMapping("/getNotificationById/{id}")
    @Operation(summary = "根据ID获取通知提醒", description = "根据通知ID查询具体的通知提醒信息")
    public ApiResponse<Notification> getNotificationById(
            @Parameter(description = "通知ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("获取通知提醒，ID：{}", id);
        Notification notification = notificationService.getNotificationById(id);
        return ApiResponse.success(notification);
    }
    
    /**
     * 新增通知提醒
     * @param notification 通知提醒信息
     * @return 操作结果
     */
    @PostMapping("/addNotification")
    @Operation(summary = "新增通知提醒", description = "添加新的通知提醒信息")
    public ApiResponse<Notification> addNotification(
            @Parameter(description = "通知提醒信息", required = true)
            @RequestBody Notification notification) {
        log.info("新增通知提醒：{}", notification);
        notificationService.addNotification(notification);
        return ApiResponse.success(notification);
    }
    
    /**
     * 更新通知提醒
     * @param notification 通知提醒信息
     * @return 操作结果
     */
    @PutMapping("/updateNotification")
    @Operation(summary = "更新通知提醒", description = "更新已有的通知提醒信息")
    public ApiResponse<Notification> updateNotification(
            @Parameter(description = "通知提醒信息", required = true)
            @RequestBody Notification notification) {
        log.info("更新通知提醒：{}", notification);
        notificationService.updateNotification(notification);
        return ApiResponse.success(notification);
    }
    
    /**
     * 删除通知提醒
     * @param id 通知ID
     * @return 操作结果
     */
    @DeleteMapping("/deleteNotification/{id}")
    @Operation(summary = "删除通知提醒", description = "根据ID删除通知提醒信息")
    public ApiResponse<Boolean> deleteNotification(
            @Parameter(description = "通知ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("删除通知提醒，ID：{}", id);
        notificationService.deleteNotification(id);
        return ApiResponse.success(true);
    }
    
    /**
     * 分页查询通知提醒
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param userId 用户ID
     * @param isRead 是否已读
     * @param relatedModule 关联模块
     * @param relatedId 关联ID
     * @return 分页结果
     */
    @GetMapping("/getNotificationPage")
    @Operation(summary = "分页查询通知提醒", description = "分页查询通知提醒信息，支持条件筛选")
    public ApiResponse<Page<Notification>> getNotificationPage(
            @Parameter(description = "页码", required = true, example = "1")
            @RequestParam("pageNum") int pageNum,
            
            @Parameter(description = "每页条数", required = true, example = "10")
            @RequestParam("pageSize") int pageSize,
            
            @Parameter(description = "用户ID")
            @RequestParam(value = "userId", required = false) Long userId,
            
            @Parameter(description = "是否已读：0-未读，1-已读")
            @RequestParam(value = "isRead", required = false) Integer isRead,
            
            @Parameter(description = "关联模块")
            @RequestParam(value = "relatedModule", required = false) String relatedModule,
            
            @Parameter(description = "关联ID")
            @RequestParam(value = "relatedId", required = false) Long relatedId) {
        log.info("分页查询通知提醒，页码：{}，每页条数：{}", pageNum, pageSize);
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("isRead", isRead);
        params.put("relatedModule", relatedModule);
        params.put("relatedId", relatedId);
        
        // 调用分页查询方法
        Page<Notification> pageResult = notificationService.getNotificationPage(pageNum, pageSize, params);
        
        return ApiResponse.success(pageResult);
    }
    
    /**
     * 根据用户ID获取通知提醒列表
     * @param userId 用户ID
     * @return 通知提醒列表
     */
    @GetMapping("/getNotificationsByUserId/{userId}")
    @Operation(summary = "根据用户ID获取通知提醒列表", description = "查询指定用户的所有通知提醒")
    public ApiResponse<List<Notification>> getNotificationsByUserId(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable("userId") Long userId) {
        log.info("获取用户的通知提醒列表，用户ID：{}", userId);
        List<Notification> notificationList = notificationService.getNotificationsByUserId(userId);
        return ApiResponse.success(notificationList);
    }
    
    /**
     * 标记通知为已读
     * @param id 通知ID
     * @return 操作结果
     */
    @PutMapping("/markNotificationAsRead/{id}")
    @Operation(summary = "标记通知为已读", description = "将未读通知标记为已读状态")
    public ApiResponse<Boolean> markNotificationAsRead(
            @Parameter(description = "通知ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        log.info("标记通知为已读，通知ID：{}", id);
        notificationService.markNotificationAsRead(id);
        return ApiResponse.success(true);
    }
}