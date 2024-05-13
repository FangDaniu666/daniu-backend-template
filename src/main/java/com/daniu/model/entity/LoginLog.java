package com.daniu.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 *
 * @author FangDaniu
 * @since  2024/05/13
 */
@TableName(value = "login_log")
@Data
public class LoginLog implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录地址
     */
    private String loginAddr;

    /**
     * 驱动名称
     */
    private String driverName;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 登录状态: 0-失败，1-成功
     */
    private Integer loginState;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}