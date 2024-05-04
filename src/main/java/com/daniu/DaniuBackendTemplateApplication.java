package com.daniu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 主类（项目启动入口）
 *
 * @author FangDaniu
 * @from daniu-backend-template
 */

@SpringBootApplication
@MapperScan("com.daniu.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class DaniuBackendTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaniuBackendTemplateApplication.class, args);
    }

}
