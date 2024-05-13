package com.daniu.model.dto.loginlog;

import lombok.Data;

/**
 * 登录日志查询参数
 *
 * @author FangDaniu
 * @since  2024/05/13
 */
@Data
public class LoginLogQueryDto {

    private String userName;
    private String loginAddr;
    private Integer loginState;
}
