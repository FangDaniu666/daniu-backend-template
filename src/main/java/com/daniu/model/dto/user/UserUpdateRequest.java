package com.daniu.model.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author FangDaniu
 * @from daniu-backend-template
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

    private static final long serialVersionUID = 1L;
}