package com.daniu.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.daniu.common.PageRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @author FangDaniu
 * @since  2024/05/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    @Min(value = 1, message = "无效id")
    private Long id;

    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
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

    @Serial
    private static final long serialVersionUID = 1L;
}