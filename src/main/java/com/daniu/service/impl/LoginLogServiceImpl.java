package com.daniu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.constant.CommonConstant;
import com.daniu.mapper.LoginLogMapper;
import com.daniu.model.entity.LoginLog;
import com.daniu.model.vo.loginLog.LoginLogEeVo;
import com.daniu.service.LoginLogService;
import org.springframework.stereotype.Service;

/**
 * 登录日志服务实现
 *
 * @author FangDaniu
 * @since  2024/05/13
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog>
        implements LoginLogService {

    @Override
    public void importLoginLog() {
        String xlsxFile = CommonConstant.DEFAULT_ASSETS_PATH + "/loginLog.xlsx";
        ExcelWriterBuilder write = EasyExcel.write(xlsxFile, LoginLogEeVo.class);
        write.sheet("登录日志").doWrite(list());
    }

}




