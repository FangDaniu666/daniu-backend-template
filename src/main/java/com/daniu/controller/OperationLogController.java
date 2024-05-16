package com.daniu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daniu.annotation.SysLog;
import com.daniu.common.BaseResponse;
import com.daniu.common.ErrorCode;
import com.daniu.common.ResultUtils;
import com.daniu.exception.BusinessException;
import com.daniu.model.dto.operationLog.OperationLogQueryDto;
import com.daniu.model.entity.OperationLog;
import com.daniu.service.OperationLogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录日志控制器
 *
 * @author FangDaniu
 * @since 2024/05/16
 */

@RestController
@RequestMapping("/admin/operationLog")
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    /**
     * 查询所有操作日志
     *
     * @param pIndex     当前页
     * @param pSize      每页大小
     * @param operateLog 条件
     * @return {@link BaseResponse }<{@link Page }<{@link OperationLog }>>
     */
    @SysLog(module = "操作日志", type = "查询操作日志")
    @PostMapping("/page/{pIndex}/{pSize}")
    public BaseResponse<Page<OperationLog>> getPage(@PathVariable Integer pIndex, @PathVariable Integer pSize, @RequestBody(required = false) OperationLogQueryDto operateLog) {
        QueryWrapper<OperationLog> qw = new QueryWrapper<>();
        if (operateLog == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        // 用户名
        if (StrUtil.isNotEmpty(operateLog.getUserName())) {
            qw.like("user_name", operateLog.getUserName());
        }
        // 操作模块
        if (StrUtil.isNotEmpty(operateLog.getOperationModule())) {
            qw.like("operation_module", operateLog.getOperationModule());
        }
        // 操作状态
        if (ObjectUtil.isNotEmpty(operateLog.getOperationState())) {
            qw.eq("operation_state", operateLog.getOperationState());
        }

        //根据id倒序
        qw.orderByDesc("id");
        Page<OperationLog> list = operationLogService.page(new Page<>(pIndex, pSize), qw);
        return ResultUtils.success(list);
    }

    /**
     * 批量删除操作日志
     *
     * @param idList 主键集合
     * @return {@link BaseResponse }<{@link Object }>
     */
    @SysLog(module = "操作日志", type = "批量删除操作日志")
    @DeleteMapping("/batchRemove")
    public BaseResponse<Boolean> batchRemove(@RequestBody List<Integer> idList) {
        boolean removed = operationLogService.removeBatchByIds(idList);
        if (!removed) throw new BusinessException(ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 导出操作日志
     *
     * @return {@link BaseResponse }<{@link Object }>
     */
    @SysLog(module = "操作日志", type = "导出操作日志")
    @GetMapping("/exportData")
    public BaseResponse<String> exportData() {
        operationLogService.importOperationLog();
        return ResultUtils.success("导出成功");
    }

}
