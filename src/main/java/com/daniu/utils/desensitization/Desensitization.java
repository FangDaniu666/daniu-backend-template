package com.daniu.utils.desensitization;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author FangDaniu
 * @from daniu-backend-template
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationSerialize.class)
public @interface Desensitization {

    /**
     * 脱敏数据类型
     */
    DesensitizationTypeEnum type() default DesensitizationTypeEnum.CUSTOMER;

    /**
     * 开始位置
     */
    int startInclude() default 0;

    /**
     * 结束位置
     */
    int endExclude() default 0;
}
