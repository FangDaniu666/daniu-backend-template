package com.daniu.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新个人信息请求
 *
 * @author FangDaniu
 * @from daniu-backend-template
 */
@Data
public class UserUpdateMyRequest implements Serializable {

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
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String userEmail;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "用户密码长度不能少于8位")
    private String userPassword;


    private static final long serialVersionUID = 1L;
}