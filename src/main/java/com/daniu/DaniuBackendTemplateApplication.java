package com.daniu;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 主类（项目启动入口）
 *
 * @author FangDaniu
 * @from daniu-backend-template
 */

@SpringBootApplication
@MapperScan("com.daniu.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)  //开启AOP功能
@EnableEncryptableProperties  //开启jasypt自动解密功能
public class DaniuBackendTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaniuBackendTemplateApplication.class, args);
    }

}
