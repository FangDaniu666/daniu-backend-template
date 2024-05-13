package com.daniu.model.vo.loginLog;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.daniu.model.converter.StateConverter;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志导出参数
 *
 * @author FangDaniu
 * @since  2024/05/13
 **/
@Data
public class LoginLogEeVo {

    @ExcelProperty(value = "编号", index = 0)
    private Integer id;

    @ExcelProperty(value = "用户名", index = 1)
    private String userName;

    @ExcelProperty(value = "登录时间", index = 2)
    @ColumnWidth(25)
    private Date loginTime;

    @ExcelProperty(value = "登录IP", index = 3)
    @ColumnWidth(20)
    private String loginIp;

    @ExcelProperty(value = "登录地点", index = 4)
    @ColumnWidth(20)
    private String loginAddr;

    @ExcelProperty(value = "浏览器", index = 5)
    @ColumnWidth(20)
    private String driverName;

    @ExcelProperty(value = "操作系统", index = 6)
    @ColumnWidth(25)
    private String osName;

    @ExcelProperty(value = "登录状态", index = 7, converter = StateConverter.class)
    @ColumnWidth(20)
    private Integer loginState;
}
