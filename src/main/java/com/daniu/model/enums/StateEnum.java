package com.daniu.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 状态枚举
 */
@Getter
@AllArgsConstructor
public enum StateEnum {

    SUCCESS(1, "成功"),
    FAILURE(0, "失败"),
    ERROR(-1, "错误");

    private final Integer value;
    private final String name;


    /**
     * 根据值进行转换
     *
     * @param value 值
     * @return StateEnum
     */
    public static StateEnum convert(Integer value) {
        return Stream.of(values())
                .filter(bean -> bean.value.equals(value))
                .findAny()
                .orElse(ERROR);
    }

    /**
     * 根据名称进行转换
     *
     * @param name 名称
     * @return StateEnum
     */
    public static StateEnum convert(String name) {
        return Stream.of(values())
                .filter(bean -> bean.name.equals(name))
                .findAny()
                .orElse(ERROR);
    }
}
