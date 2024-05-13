package com.daniu.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 网络工具类
 *
 * @author FangDaniu
 * @since  2024/05/11
 */
@Slf4j
public class NetUtils {

    /**
     * 获取客户端 IP 地址
     *
     * @param request 请求
     * @return {@link String }
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                ip = getIp();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }

        return ip == null ? "127.0.0.1" : ip;
    }

    /**
     * 获取本机公网IP
     */
    public static String getIp() {
        String ip = null;
        String test = "http://checkip.amazonaws.com/";
        StringBuilder inputLine = new StringBuilder();
        BufferedReader in = null;

        try {
            URL url = new URL(test);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));

            String read;
            while ((read = in.readLine()) != null) {
                inputLine.append(read);
            }

            ip = inputLine.toString();
        } catch (Exception var16) {
            log.error("获取网络IP地址异常，具体原因: ", var16);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var15) {
                    log.error("关闭流异常，具体原因: ", var15);
                }
            }
        }

        return ip;
    }

}
