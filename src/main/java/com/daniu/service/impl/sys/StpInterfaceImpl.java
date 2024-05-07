package com.daniu.service.impl.sys;

/**
 * @version 1.0
 * @author FangDaniu
 * @Date 2023/11/15 16:45
 */

import cn.dev33.satoken.stp.StpInterface;
import com.daniu.model.entity.User;
import com.daniu.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserService userService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        // todo 自行添加逻辑
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        // todo 自行添加逻辑
        User user = userService.getLoginUser(loginId);
        String userType = user.getUserType();
        if (userType != null) list.add(userType);
        return list;
    }

}
