package com.daniu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.mapper.OperationLogMapper;
import com.daniu.model.entity.OperationLog;
import com.daniu.model.vo.operationLog.OperationLogEeVo;
import com.daniu.service.OperationLogService;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务impl
 *
 * @author FangDaniu
 * @since  2024/05/15
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
    implements OperationLogService {

    @Override
    public void importOperationLog() {
        EasyExcel.write("D:\\操作日志.xlsx", OperationLogEeVo.class).sheet("操作日志").doWrite(baseMapper.selectList(null));
    }

}




