package com.daniu;

import com.daniu.constant.CommonConstant;
import com.daniu.quartz.QuartzExecute;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
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
public class DaniuBackendTemplateApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DaniuBackendTemplateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // todo 定时任务启动，可视情况予以开闭
        QuartzExecute.doQuartzTask(CommonConstant.QUARTZ_TIME_PERIOD);
    }

}
