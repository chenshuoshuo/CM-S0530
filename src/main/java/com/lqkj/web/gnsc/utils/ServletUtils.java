package com.lqkj.web.gnsc.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * serlvet 工具类
 */
public class ServletUtils {
    /**
     * 返回用IP地址
     */
    public static String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取请求地址
     * @param request 请求对象
     * @return 请求地址
     */
    public static String createBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() //服务器地址
                + ":" + request.getServerPort()//端口号
                + request.getContextPath();//项目名称
    }
}
