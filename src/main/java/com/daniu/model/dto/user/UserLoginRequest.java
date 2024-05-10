package com.daniu.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author FangDaniu
 * @from daniu-backend-template
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 4, message = "账号错误")
    private String userAccount;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "密码错误")
    private String userPassword;

    @Email(message = "邮箱格式不正确")
    private String userEmail;
}
