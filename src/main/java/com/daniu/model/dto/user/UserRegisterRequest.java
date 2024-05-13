package com.daniu.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author FangDaniu
 * @since  2024/05/4
 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 4, message = "用户账号长度不能少于4位")
    private String userAccount;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "用户密码长度不能少于8位")
    private String userPassword;

    @NotBlank(message = "确认密码不能为空")
    @Size(min = 8, message = "确认密码长度不能少于8位")
    private String checkPassword;

    private String userName;

    @Email(message = "邮箱格式不正确")
    private String userEmail;
}
