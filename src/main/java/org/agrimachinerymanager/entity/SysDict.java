package org.agrimachinerymanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据字典表实体类
 */
@Data
@TableName("sys_dict")
public class SysDict {

    /**
     * 字典ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 字典类型：machinery_type-农机类型，maintain_type-维护类型，operation_type-作业类型，machinery_status-农机状态
     */
    private String type;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 排序（升序）
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}