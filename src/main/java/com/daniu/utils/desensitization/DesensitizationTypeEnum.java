package com.daniu.utils.desensitization;

/**
 * 脱敏类型枚举
 *
 * @author FangDaniu
 * @since 2024/05/9
 */
public enum DesensitizationTypeEnum {
    // 自定义
    CUSTOMER,
    // 座机
    FIXED_PHONE,
    // 地址
    ADDRESS,
    // 身份证号
    ID_CARD,
    // 银行卡
    BANK_CARD,
    // 车牌号
    CAR_LICENSE,
    // 中文名
    CHINESE_NAME,
    // 密码
    PASSWORD,
    // ipv4
    IPV4,
    // 手机号
    MOBILE_PHONE,
    // 电子邮件
    EMAIL
}
