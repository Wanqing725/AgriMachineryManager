package org.agrimachinerymanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 作业调度任务表实体类
 */
@Data
@TableName("operation_task")
public class OperationTask {

    /**
     * 任务ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 农机ID（关联machinery表）
     */
    private Long machineryId;

    /**
     * 地块ID（关联farmland表）
     */
    private Long farmlandId;

    /**
     * 作业类型编码（关联sys_dict表：耕地/播种/收割等）
     */
    private String operationType;

    /**
     * 计划开始时间
     */
    private LocalDateTime planStartTime;

    /**
     * 计划结束时间
     */
    private LocalDateTime planEndTime;

    /**
     * 计划作业量（亩）
     */
    private BigDecimal planQuantity;

    /**
     * 实际开始时间
     */
    private LocalDateTime actualStartTime;

    /**
     * 实际结束时间
     */
    private LocalDateTime actualEndTime;

    /**
     * 实际作业量（亩）
     */
    private BigDecimal actualQuantity;

    /**
     * 实际油耗（L）
     */
    private BigDecimal fuelConsumption;

    /**
     * 状态：1-待执行，2-执行中，3-已完成，4-已取消
     */
    private Integer status;

    /**
     * 负责人ID（关联sys_user表）
     */
    private Long responsibleUserId;

    /**
     * 备注（如异常情况说明）
     */
    private String remark;

    /**
     * 创建人ID（关联sys_user表）
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}