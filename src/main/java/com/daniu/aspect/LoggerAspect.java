package com.daniu.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.daniu.annotation.SysLog;
import com.daniu.model.dto.user.UserLoginRequest;
import com.daniu.model.dto.user.UserRegisterRequest;
import com.daniu.model.entity.OperationLog;
import com.daniu.model.entity.User;
import com.daniu.service.OperationLogService;
import com.daniu.service.UserService;
import com.daniu.utils.HttpUtil;
import com.daniu.utils.NetUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * 日志切面
 *
 * @author FangDaniu
 * @since 2024/05/16
 */
@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private UserService userService;

    final ThreadLocal<OperationLog> logThreadLocal = new ThreadLocal<>();

    @Pointcut(value = "execution(* com.daniu.controller..*(..))")
    public void logPointCut() {

    }


    /**
     * 前置通知
     */
    @Before("logPointCut()")
    public void beforeLogger(JoinPoint joinPoint) {
        // 获取方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // 获取方法上的注解
        SysLog sysLog = method.getAnnotation(SysLog.class);

        String userAccount = getUserAccount(joinPoint, method);

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        if (sysLog != null) {
            String ip = NetUtils.getIpAddress(request);

            OperationLog operationLog = new OperationLog();
            if (userAccount.isEmpty()) {
                User user = userService.getLoginUser(StpUtil.getLoginId());
                operationLog.setUserName(user.getUserName());
            } else {
                operationLog.setUserName(userAccount);
            }
            operationLog.setOperation(sysLog.type());
            operationLog.setOperationModule(sysLog.module());
            operationLog.setOperationTime(new Date());
            operationLog.setOperationIp(NetUtils.getIpAddress(request));
            operationLog.setOperationAddr(HttpUtil.getProvince(ip) + HttpUtil.getCity(ip));

            logThreadLocal.set(operationLog);
        }
    }

    /**
     * 获取登录/注册用户账户
     *
     * @param joinPoint 连接点
     * @param method    方法
     * @return {@link String }
     */
    private static String getUserAccount(JoinPoint joinPoint, Method method) {
        String userAccount = "";
        if (method.getName().equals("userLogin") || method.getName().equals("userRegister")) {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof UserLoginRequest userLoginRequest) {
                    userAccount = userLoginRequest.getUserAccount();
                }
                if (arg instanceof UserRegisterRequest userRegisterRequest) {
                    userAccount = userRegisterRequest.getUserAccount();
                }
            }
        }
        return userAccount;
    }

    /**
     * 后置通知
     */
    @AfterReturning("logPointCut()")
    public void afterLogger() {
        OperationLog operationLog = logThreadLocal.get();

        if (operationLog != null) {
            operationLog.setOperationState(1);
            operationLogService.save(operationLog);
            logThreadLocal.remove();
        }

    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "logPointCut()", throwing = "throwable")
    public void throwingLogger(Throwable throwable) {
        // 保存操作日志
        OperationLog operationLog = logThreadLocal.get();
        if (operationLog != null) {
            operationLog.setOperationState(0);
            String throwableStr = throwable.toString();
            if (throwableStr.contains(":")) {
                throwableStr = throwableStr.substring(throwableStr.indexOf(":") + 1);
            }
            log.error(throwableStr);

            // 调用具体的 service 保存到数据库中
            operationLogService.save(operationLog);

            // 移除本地线程数据
            logThreadLocal.remove();
        }

    }

}
