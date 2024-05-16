package com.daniu.model.vo.operationLog;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.daniu.model.converter.StateConverter;
import lombok.Data;
import java.util.Date;

/**
 * 操作日志导出参数
 *
 * @author FangDaniu
 * @since  2024/05/15
 */

@Data
public class OperationLogEeVo {

    @ExcelProperty(value = "编号", index = 0)
    private Integer id;

    @ExcelProperty(value = "用户名", index = 1)
    private String userName;

    @ExcelProperty(value = "操作", index = 2)
    @ColumnWidth(20)
    private String operation;

    @ExcelProperty(value = "操作模块", index = 3)
    @ColumnWidth(20)
    private String operationModule;

    @ExcelProperty(value = "操作时间", index = 4)
    @ColumnWidth(20)
    private Date operationTime;

    @ExcelProperty(value = "操作IP", index = 5)
    @ColumnWidth(20)
    private String operationIp;

    @ExcelProperty(value = "操作地址", index = 6)
    @ColumnWidth(20)
    private String operationAddr;

    @ExcelProperty(value = "操作状态", index = 7, converter = StateConverter.class)
    @ColumnWidth(20)
    private Integer operationState;
}
