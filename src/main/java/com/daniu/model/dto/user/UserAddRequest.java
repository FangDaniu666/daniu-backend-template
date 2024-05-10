package com.daniu.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author FangDaniu
 * @from daniu-backend-template
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 4, message = "用户账号长度不能少于4位")
    private String userAccount;

    /**
     *  用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String userEmail;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户类型: user, admin，ban
     */
    private String userType;

    private static final long serialVersionUID = 1L;
}