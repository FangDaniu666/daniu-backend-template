package com.daniu.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daniu.common.BaseResponse;
import com.daniu.common.ErrorCode;
import com.daniu.common.ResultUtils;
import com.daniu.constant.UserConstant;
import com.daniu.exception.BusinessException;
import com.daniu.model.dto.loginlog.LoginLogQueryDto;
import com.daniu.model.entity.LoginLog;
import com.daniu.service.LoginLogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录日志接口
 *
 * @author FangDaniu
 * @since  2024/05/13
 */
@RestController
@RequestMapping("/admin/loginLog")
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    /**
     * 查询登录日志
     *
     * @param pIndex   当前页
     * @param pSize    每页大小
     * @param loginLog 条件
     * @return 结果
     */
    //@SysLog(module = "登录日志", type = "查询")
    @PostMapping("/page/{pIndex}/{pSize}")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<LoginLog>> getPage(@PathVariable Integer pIndex, @PathVariable Integer pSize, @RequestBody(required = false) LoginLogQueryDto loginLog) {
        QueryWrapper<LoginLog> qw = new QueryWrapper<>();
        if (loginLog == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        // 用户名
        if (StrUtil.isNotEmpty(loginLog.getUserName())) {
            qw.like("user_name", loginLog.getUserName());
        }
        // 登录地址
        if (StrUtil.isNotEmpty(loginLog.getLoginAddr())) {
            qw.like("login_addr", loginLog.getLoginAddr());
        }
        // 登录状态
        if (ObjectUtil.isNotEmpty(loginLog.getLoginState())) {
            qw.eq("login_state", loginLog.getLoginState());
        }

        //根据id倒序
        QueryWrapper<LoginLog> loginLogQueryWrapper = qw.orderByDesc("id");
        Page<LoginLog> loginLogPage = loginLogService.page(new Page<>(pIndex, pSize), loginLogQueryWrapper);
        return ResultUtils.success(loginLogPage);
    }

    /**
     * 批量删除登录日志
     *
     * @param idList 主键集合
     * @return 结果
     */
    //@SysLog(module = "登录日志", type = "批量删除")
    @DeleteMapping("/batchRemove")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> batchRemove(@RequestBody List<Integer> idList) {
        boolean removed = loginLogService.removeBatchByIds(idList);
        if (!removed) throw new BusinessException(ErrorCode.OPERATION_ERROR, "删除失败");
        return ResultUtils.success(true);
    }

    /**
     * 导出
     */
    //@SysLog(module = "登录日志", type = "导出")
    @GetMapping("/exportData")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<String> exportData() {
        loginLogService.importLoginLog();
        return ResultUtils.success("导出成功");
    }


}
