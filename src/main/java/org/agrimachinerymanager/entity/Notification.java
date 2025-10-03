package org.agrimachinerymanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知提醒表实体类
 */
@Data
@TableName("notification")
public class Notification {

    /**
     * 通知ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 关联ID（如农机ID）
     */
    private Long relatedId;

    /**
     * 关联模块：maintain-维护提醒
     */
    private String relatedModule;

    /**
     * 接收人ID（关联sys_user表）
     */
    private Long userId;

    /**
     * 是否已读：0-未读，1-已读
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}