package com.daniu.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daniu.constant.UserConstant;
import com.daniu.model.vo.UserWithEmailVO;
import com.daniu.utils.HttpUtil;
import com.daniu.utils.NetUtils;
import com.daniu.utils.NullAwareBeanUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.daniu.common.BaseResponse;
import com.daniu.model.dto.user.DeleteRequest;
import com.daniu.common.ErrorCode;
import com.daniu.common.ResultUtils;
import com.daniu.exception.BusinessException;
import com.daniu.exception.ThrowUtils;
import com.daniu.model.dto.user.*;
import com.daniu.model.entity.User;
import com.daniu.model.vo.LoginUserVO;
import com.daniu.model.vo.UserVO;
import com.daniu.service.UserService;

/**
 * 用户接口
 *
 * @author FangDaniu
 * @from daniu-backend-template
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    // 登录相关

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return BaseResponse
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@Validated @RequestBody UserRegisterRequest userRegisterRequest) {
        log.info("userRegister");
        if (userRegisterRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        User user = new User();
        user.setUserPassword(userRegisterRequest.getUserPassword());
        user.setUserEmail(userRegisterRequest.getUserEmail());
        user.setCreateTime(DateUtil.date());

        long result = userService.userRegister(userRegisterRequest.getUserAccount(),
                userRegisterRequest.getCheckPassword(), user);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return BaseResponse
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@Validated @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);

        String ip = NetUtils.getIpAddress(request);
        String region = HttpUtil.getRegion(ip);
        log.info(region);

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword);
        return ResultUtils.success(loginUserVO);
    }


    /**
     * 用户注销
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        if (!StpUtil.isLogin()) throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        Object loginId = StpUtil.getLoginId();
        boolean result = userService.userLogout(loginId);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser() {
        // 先判断是否已登录
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询
        User user = userService.getLoginUser(StpUtil.getLoginId());
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    // 增删改查

    /**
     * 创建用户
     *
     * @param userAddRequest
     * @return BaseResponse
     */
    @PostMapping("/add")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@Validated @RequestBody UserAddRequest userAddRequest) {
        if (userAddRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        User user = new User();
        NullAwareBeanUtils.copyPropertiesIgnoreEmpty(userAddRequest, user);
        boolean result = userService.saveUser(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * 删除用户
     *
     * @param deleteRequest
     * @return BaseResponse
     */
    @PostMapping("/delete")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@Validated @RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        boolean b = userService.deleteUserById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest
     * @return BaseResponse
     */
    @PostMapping("/update")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> updateUser(@Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        User user = new User();
        NullAwareBeanUtils.copyPropertiesIgnoreEmpty(userUpdateRequest, user);
        boolean result = userService.updateUser(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * 更新个人信息
     *
     * @param userUpdateMyRequest
     * @return BaseResponse
     */
    @PostMapping("/update/my")
    public BaseResponse<Long> updateMyUser(@Validated @RequestBody UserUpdateMyRequest userUpdateMyRequest) {
        if (userUpdateMyRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        // 先判断是否已登录
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Object loginId = StpUtil.getLoginId();
        User user = new User();
        user.setId(Long.parseLong(loginId.toString()));
        NullAwareBeanUtils.copyPropertiesIgnoreEmpty(userUpdateMyRequest, user);
        boolean result = userService.updateMyUser(loginId, user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * 根据 id 获取用户（仅管理员）
     *
     * @param id
     * @return BaseResponse
     */
    @GetMapping("/get")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(@Validated @Min(value = 1, message = "无效id") long id) {
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类
     *
     * @param id
     * @return BaseResponse
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVOById(@Validated @Min(value = 1, message = "无效id") long id) {
        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest
     * @return BaseResponse
     */
    @PostMapping("/list/page")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, size), userService.getQueryWrapper(userQueryRequest));
        return ResultUtils.success(userPage);
    }

    /**
     * 分页获取用户封装列表
     *
     * @param userQueryRequest
     * @return BaseResponse
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<UserVO> userVOPage = userService.getUserVOPage(userQueryRequest);
        return ResultUtils.success(userVOPage);
    }


    /**
     * 邮箱字段脱敏
     *
     * @param id
     * @return BaseResponse
     */
    @GetMapping("/get/mail/vo")
    public BaseResponse<UserWithEmailVO> getUserWithEmailById(@Validated @Min(value = 1, message = "无效id") long id) {
        User user = userService.getById(id);
        UserWithEmailVO userWithEmailVO = new UserWithEmailVO();
        NullAwareBeanUtils.copyPropertiesIgnoreEmpty(user, userWithEmailVO);
        return ResultUtils.success(userWithEmailVO);
    }

}
