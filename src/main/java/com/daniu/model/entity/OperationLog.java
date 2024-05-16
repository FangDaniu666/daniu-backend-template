package com.daniu.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 *
 * @author FangDaniu
 * @since  2024/05/15
 */
@TableName(value ="operation_log")
@Data
public class OperationLog implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 操作模块
     */
    private String operationModule;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 操作ip
     */
    private String operationIp;

    /**
     * 操作地址
     */
    private String operationAddr;

    /**
     * 操作状态: 0-失败，1-成功
     */
    private Integer operationState;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}