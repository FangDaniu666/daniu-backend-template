package com.daniu.model.vo;

import com.daniu.utils.desensitization.Desensitization;
import com.daniu.utils.desensitization.DesensitizationTypeEnum;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserWithEmailVO implements Serializable {

    /**
     * id
     */
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
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     *  用户邮箱
     */
    @Desensitization(type = DesensitizationTypeEnum.EMAIL)
    private String userEmail;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
