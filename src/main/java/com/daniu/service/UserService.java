package com.daniu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daniu.model.dto.user.UserQueryRequest;
import com.daniu.model.entity.User;
import com.daniu.model.vo.LoginUserVO;
import com.daniu.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户服务
 *
 * @author FangDaniu
 * @since  2024/05/4
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param checkPassword 校验密码
     * @param user          注册用户
     * @return 新用户 id
     */
    long userRegister(String userAccount, String checkPassword, User user);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param loginId 登录id
     * @return {@link User }
     */
    User getLoginUser(Object loginId);

    /**
     * 是否为管理员
     *
     * @param user 用户
     * @return boolean
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @param loginId 已登录用户id
     * @return boolean
     */
    boolean userLogout(Object loginId);

    /**
     * 新增用户
     *
     * @param user 用户
     * @return boolean
     */
    boolean saveUser(User user);

    /**
     * 删除用户
     *
     * @param deleteId 待删除用户id
     * @return boolean
     */
    boolean deleteUserById(Long deleteId);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return boolean
     */
    boolean updateUser(User user);

    /**
     * 更新当前登录用户信息
     *
     * @param loginId 已登录用户id
     * @param user    用户
     * @return boolean
     */
    boolean updateMyUser(Object loginId, User user);

    /**
     * 分页获取脱敏的用户信息
     *
     * @param userQueryRequest 用户查询请求
     * @return {@link Page }<{@link UserVO }>
     */
    Page<UserVO> getUserVOPage(UserQueryRequest userQueryRequest);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @param user 已登录用户
     * @return {@link LoginUserVO }
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user 用户
     * @return {@link UserVO }
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList 用户列表
     * @return {@link List }<{@link UserVO }>
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询请求
     * @return {@link QueryWrapper }<{@link User }>
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

}
