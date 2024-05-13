package com.daniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daniu.model.entity.LoginLog;

/**
 * 登录日志服务
 *
 * @author FangDaniu
 * @since  2024/05/12
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 导出登录日志
     */
    void importLoginLog();
}
