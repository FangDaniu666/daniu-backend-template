package com.daniu.model.dto.operationLog;

import lombok.Data;

/**
 * 操作日志查询参数
 *
 * @author FangDaniu
 * @since  2024/05/15
 */
@Data
public class OperationLogQueryDto {

    private String userName;
    private String operationModule;
    private Integer operationState;
}
