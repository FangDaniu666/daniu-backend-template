package com.daniu.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.GlobalBouncyCastleProvider;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装 hutool JWT工具
 *
 * @author FangDaniu
 * @since 2024/05/19
 */
@Slf4j
public class JwtUtils {

    /**
     * 盐值很重要，不能泄漏，且每个项目都应该不一样，可以放到配置文件中
     */
    private static final String key = "2407595a-e5e3-4d93-b422-d10433e8c769";

    public static String createToken(Long id, String userName) {
        log.info("开始生成JWT token，id：{}，userName：{}", id, userName);
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR, 24);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 内容
        payload.put("id", id);
        payload.put("username", userName);
        String token = JWTUtil.createToken(payload, key.getBytes());
        log.info("生成JWT token：{}", token);
        return token;
    }

    public static boolean validate(String token) {
        try {
            log.info("开始JWT token校验，token：{}", token);
            GlobalBouncyCastleProvider.setUseBouncyCastle(false);
            JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
            // validate包含了verify
            boolean validate = jwt.validate(0);
            log.info("JWT token校验结果：{}", validate);
            return validate;
        } catch (Exception e) {
            log.error("JWT token校验异常", e);
            return false;
        }
    }

    public static JSONObject getJSONObject(String token) {
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        log.info("根据token获取原始内容：{}", payloads);
        return payloads;
    }

    public static void main(String[] args) {
        createToken(1L, "FangDaniu");

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3MTYxMjk4NzYsImlkIjoxLCJleHAiOjE3MTYyMTYyNzYsImlhdCI6MTcxNjEyOTg3NiwidXNlcm5hbWUiOiJGYW5nRGFuaXUifQ.NGrVz-0VrabRgQAFnLgITBU49GaMsRXJL4cx5F4QQ1g";
        validate(token);

        getJSONObject(token);
    }
}
