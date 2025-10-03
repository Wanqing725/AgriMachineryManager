package org.agrimachinerymanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统操作日志表实体类
 */
@Data
@TableName("sys_operate_log")
public class SysOperateLog {

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作人ID（关联sys_user表）
     */
    private Long userId;

    /**
     * 操作类型：add-新增，update-修改，delete-删除，login-登录
     */
    private String operateType;

    /**
     * 操作模块：machinery-农机管理，maintain-维护管理，operation-调度管理
     */
    private String operateModule;

    /**
     * 操作内容描述
     */
    private String operateContent;

    /**
     * 操作IP地址
     */
    private String operateIp;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;
}