package com.daniu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.exception.ThrowUtils;
import com.daniu.model.entity.LoginLog;
import com.daniu.model.enums.UserRoleEnum;
import com.daniu.service.LoginLogService;
import com.daniu.utils.HttpUtil;
import com.daniu.utils.NetUtils;
import com.daniu.utils.NullAwareBeanUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import com.daniu.common.ErrorCode;
import com.daniu.constant.CommonConstant;
import com.daniu.exception.BusinessException;
import com.daniu.mapper.UserMapper;
import com.daniu.model.dto.user.UserQueryRequest;
import com.daniu.model.entity.User;
import com.daniu.model.vo.LoginUserVO;
import com.daniu.model.vo.UserVO;
import com.daniu.service.UserService;
import com.daniu.utils.SqlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 * @author FangDaniu
 * @since 2024/05/4
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private LoginLogService loginLogService;

    // 盐值，混淆密码
    private static final String SALT = "80bcac91-6c73-46e2-86c4-47ed0eb10496";

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param checkPassword 校验密码
     * @param user          注册用户
     * @return 新用户 id
     */
    @Override
    @Transactional
    @CacheEvict(value = "userCache", allEntries = true)
    public long userRegister(String userAccount, String checkPassword, User user) {
        // 密码和校验密码是否相同
        if (!user.getUserPassword().equals(checkPassword))
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            // 密码加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + user.getUserPassword()).getBytes());
            // 插入数据
            user.setUserPassword(encryptPassword);
            user.setUserAccount(userAccount);
            boolean saveResult = this.save(user);
            if (!saveResult) throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            return user.getId();
        }
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户信息
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);

        // 登录日志
        String ip = NetUtils.getIpAddress(request);
        String region = HttpUtil.getRegion(ip);
        log.info(region);

        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(userAccount);
        loginLog.setLoginTime(new Date());
        loginLog.setLoginIp(ip);
        loginLog.setLoginAddr(HttpUtil.getProvince(ip) + " " + HttpUtil.getCity(ip));
        loginLog.setDriverName(HttpUtil.getBrowserName(request) + " " + HttpUtil.getBrowserVersion(request));
        loginLog.setOsName(HttpUtil.getOsName(request));
        loginLog.setLoginState(1);

        // 判断用户是否为空
        if (user == null) {
            log.info("user login failed, user_account cannot match user_password");
            loginLog.setLoginState(0);
            loginLogService.save(loginLog);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }

        // 记录用户的登录态
        StpUtil.login(user.getId());
        loginLogService.save(loginLog);
        return this.getLoginUserVO(user);
    }

    /**
     * 获取当前登录用户
     *
     * @param loginId 已登录用户id
     * @return {@link User }
     */
    @Override
    @Cacheable(value = "loginUserCache", key = "#loginId")
    public User getLoginUser(Object loginId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", loginId);
        User currentUser = this.baseMapper.selectOne(queryWrapper);
        if (currentUser == null) throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserType());
    }

    /**
     * 用户注销
     *
     * @param loginId 已登录用户id
     * @return boolean
     */
    @Override
    @CacheEvict(value = "loginUserCache", key = "#loginId")
    public boolean userLogout(Object loginId) {
        // 移除登录态
        StpUtil.logout();
        return true;
    }

    // 增删改查

    /**
     * 新增用户
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    @Transactional
    @CacheEvict(value = "userCache", allEntries = true)
    public boolean saveUser(User user) {
        return save(user);
    }

    /**
     * 删除用户
     *
     * @param deleteId 待删除用户id
     * @return boolean
     */
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "userCache", allEntries = true),
            @CacheEvict(value = "loginUserCache", key = "#deleteId")
    })
    public boolean deleteUserById(Long deleteId) {
        StpUtil.logout(deleteId);
        StpUtil.kickout(deleteId);
        return removeById(deleteId);
    }

    /**
     * 更新用户
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "userCache", allEntries = true),
            @CacheEvict(value = "loginUserCache", key = "#user.getId()")
    })
    public boolean updateUser(User user) {
        return updateById(user);
    }

    /**
     * 更新当前登录用户信息
     *
     * @param loginId 已登录用户id
     * @param user    用户
     * @return boolean
     */
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "userCache", allEntries = true),
            @CacheEvict(value = "loginUserCache", key = "#loginId")
    })
    public boolean updateMyUser(Object loginId, User user) {
        return updateById(user);
    }

    @Override
    @Cacheable(value = "userCache", key = "#userQueryRequest.current + '-' + #userQueryRequest.pageSize")
    public Page<UserVO> getUserVOPage(UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<User> userPage = page(new Page<>(current, size), getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
        List<UserVO> userVO = getUserVO(userPage.getRecords());
        userVOPage.setRecords(userVO);
        return userVOPage;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        NullAwareBeanUtils.copyPropertiesIgnoreEmpty(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        NullAwareBeanUtils.copyPropertiesIgnoreEmpty(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询请求
     * @return {@link QueryWrapper }<{@link User }>
     */
    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        Long id = userQueryRequest.getId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}
