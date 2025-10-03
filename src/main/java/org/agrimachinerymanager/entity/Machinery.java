package org.agrimachinerymanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 农机档案表实体类
 */
@Data
@TableName("machinery")
public class Machinery {

    /**
     * 农机ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 农机编号（唯一标识）
     */
    private String machineryCode;

    /**
     * 农机类型编码（关联sys_dict表）
     */
    private String typeCode;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * 出厂编号
     */
    private String factoryNumber;

    /**
     * 购买日期
     */
    private LocalDate buyDate;

    /**
     * 额定功率（kW）
     */
    private BigDecimal power;

    /**
     * 归属部门
     */
    private String department;

    /**
     * 负责人ID（关联sys_user表）
     */
    private Long responsibleUserId;

    /**
     * 状态编码（关联sys_dict表：正常/待维护/维修中/报废）
     */
    private String status;

    /**
     * 农机照片URL
     */
    private String photoUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}