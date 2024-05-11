package com.daniu.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.util.ResourceUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * http 工具类
 */
@Slf4j
public class HttpUtil {

    /**
     * 获取浏览器名字
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getBrowserName(HttpServletRequest request) {
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        return ua.getBrowser().toString();
    }

    /**
     * 获取浏览器版本
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        return ua.getVersion();
    }

    /**
     * 获取操作系统名称
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getOsName(HttpServletRequest request) {
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        return ua.getOs().toString();
    }

    /**
     * 获取ip地址
     *
     * @return {@link String}
     */
    public static String getIp() {
        String ip = null;
        String test = "http://checkip.amazonaws.com/";
        StringBuilder inputLine = new StringBuilder();
        BufferedReader in = null;

        try {
            URL url = new URL(test);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));

            String read;
            while((read = in.readLine()) != null) {
                inputLine.append(read);
            }

            ip = inputLine.toString();
        } catch (Exception var16) {
            log.error("获取网络IP地址异常，这是具体原因: ", var16);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        if (ip == null) {
            ip = "127.0.0.1";
            log.info("获取网络IP地址异常, 赋值默认ip: 【{}】", ip);
        }

        return ip;
    }

    /**
     * 获取格式化ip地址
     */
    public static String getIpAddress() {
        String region = "";
        try {
            // 1、创建 searcher 对象
            File file = ResourceUtils.getFile("classpath:ip2region.xdb");
            String dbPath = file.getPath();
            Searcher searcher = Searcher.newWithFileOnly(dbPath);
            // 2、查询
            long sTime = System.nanoTime();
            region = searcher.search(getIp());
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - sTime);
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return region;
    }

    /**
     * 获取省份
     *
     * @return 省份
     */
    public static String getProvince() {
        String province = null;
        try {
            String str = getIpAddress();
            String a = str.substring(0, str.lastIndexOf("|"));
            String b = a.substring(0, a.lastIndexOf("|"));
            province = b.substring(b.lastIndexOf("|") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return province;
    }


    /**
     * 获取城市
     *
     * @return 城市
     */
    public static String getCity() {
        String city = null;
        try {
            String str = getIpAddress();
            String a = str.substring(0, str.lastIndexOf("|"));
            city = a.substring(a.lastIndexOf("|") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return city;
    }

    /*public static void main(String[] args) {
        System.out.println(getIpAddress());
    }*/

}
