package com.daniu.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * mybatis-plus代码生成器
 *
 * @author FangDaniu
 * @since 2024/05/18
 */

public class CodeGenerator {
    public static void main(String[] args) {

        // 数据源配置
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/dbt_db",
                        "root", "FangDaniu666")
                .globalConfig(builder -> {
                    builder.author("FangDaniu")        // 设置作者
                            // .enableSwagger()        // 开启 swagger 模式 默认值:false
                            .disableOpenDir()       // 禁止打开输出目录 默认值:true
                            .commentDate("yyyy-MM-dd") // 注释日期
                            .dateType(DateType.ONLY_DATE)   //定义生成的实体类中日期类型 DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")// 指定输出目录
                    ;
                })
                .packageConfig(builder -> builder.parent("com.daniu")
                        .moduleName("generate")
                        .entity("model.entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .xml("mapper.xml")
                        .controller("controller"))
                .strategyConfig(builder -> {
                    builder.addInclude("user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            .mapperBuilder()
                            .superClass(BaseMapper.class)
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sXml")
                            .enableFileOverride();//删除已存在的
                })
                .injectionConfig(builder -> {

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}

