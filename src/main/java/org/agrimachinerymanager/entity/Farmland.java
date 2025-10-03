package org.agrimachinerymanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 地块信息表实体类
 */
@Data
@TableName("farmland")
public class Farmland {

    /**
     * 地块ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 地块编码
     */
    private String landCode;

    /**
     * 地块名称
     */
    private String name;

    /**
     * 面积（亩）
     */
    private BigDecimal area;

    /**
     * 位置描述
     */
    private String location;

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