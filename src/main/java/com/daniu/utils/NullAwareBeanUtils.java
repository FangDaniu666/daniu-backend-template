package com.daniu.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.util.Arrays;

public class NullAwareBeanUtils {

    /**
     * 拷贝属性，将空字符串视为空值
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreEmpty(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 获取属性值为null的属性名数组
     * @param source 源对象
     * @return 属性名数组
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        return Arrays.stream(pds)
                .filter(pd -> src.getPropertyValue(pd.getName()) == null ||
                        (src.getPropertyValue(pd.getName()) instanceof String &&
                                ((String) src.getPropertyValue(pd.getName())).isEmpty()))
                .map(java.beans.PropertyDescriptor::getName)
                .toArray(String[]::new);
    }

}

