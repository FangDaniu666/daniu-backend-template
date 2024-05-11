package com.daniu.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.util.ResourceUtils;

import java.io.File;

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
     * 获取格式化ip地址
     */
    public static String getRegion(String ip) {
        String region = "";
        try {
            // 1、创建 searcher 对象
            File file = ResourceUtils.getFile("classpath:ip2region.xdb");
            String dbPath = file.getPath();
            Searcher searcher = Searcher.newWithFileOnly(dbPath);
            // 2、查询
            region = searcher.search(ip);
        } catch (Exception e) {
            log.error("获取ip地址信息失败");
        }

        return region;
    }

    /**
     * 获取省份
     *
     * @return 省份
     */
    public static String getProvince(String ip) {
        String province = null;
        try {
            String str = getRegion(ip);
            String a = str.substring(0, str.lastIndexOf("|"));
            String b = a.substring(0, a.lastIndexOf("|"));
            province = b.substring(b.lastIndexOf("|") + 1);
        } catch (Exception e) {
            log.error("获取省份信息失败");
        }
        return province;
    }


    /**
     * 获取城市
     *
     * @return 城市
     */
    public static String getCity(String ip) {
        String city = null;
        try {
            String str = getRegion(ip);
            String a = str.substring(0, str.lastIndexOf("|"));
            city = a.substring(a.lastIndexOf("|") + 1);
        } catch (Exception e) {
            log.error("获取城市信息失败");
        }

        return city;
    }


}
