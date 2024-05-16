package com.daniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daniu.model.dto.operationLog.OperationLogQueryDto;
import com.daniu.model.entity.OperationLog;

/**
 * 操作日志服务
 *
 * @author FangDaniu
 * @since  2024/05/15
 */
public interface OperationLogService extends IService<OperationLog> {

    void importOperationLog();

}
