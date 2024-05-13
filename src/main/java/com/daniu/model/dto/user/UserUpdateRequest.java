package com.daniu.model.dto.user;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author FangDaniu
 * @since  2024/05/4
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    @Min(value = 1, message = "无效id")
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

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