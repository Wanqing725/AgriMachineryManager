package org.agrimachinerymanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 农机维护记录表实体类
 */
@Data
@TableName("maintain_record")
public class MaintainRecord {

    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 农机ID（关联machinery表）
     */
    private Long machineryId;

    /**
     * 维护类型编码（关联sys_dict表：保养/维修）
     */
    private String maintainType;

    /**
     * 维护时间
     */
    private LocalDateTime maintainTime;

    /**
     * 更换配件（多个用逗号分隔）
     */
    private String parts;

    /**
     * 维护费用（元）
     */
    private BigDecimal cost;

    /**
     * 维护人员
     */
    private String maintainer;

    /**
     * 下次维护时间
     */
    private LocalDate nextMaintainTime;

    /**
     * 下次维护周期（天，用于自动计算）
     */
    private Integer nextMaintainCycle;

    /**
     * 维护描述（故障原因/保养内容）
     */
    private String description;

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