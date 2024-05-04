package com.daniu.model.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.daniu.common.PageRequest;

import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @author FangDaniu
 * @from daniu-backend-template
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户类型：user/admin/ban
     */
    private String userType;

    private static final long serialVersionUID = 1L;
}