package com.demo.gateway.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * 类描述:    获取http请求头中的源IP（X-Forwarded-For）
 */
public class RemoteIpUtil {

    private static final String[] PROXY_REMOTE_IP_ADDRESS = { "X-Forwarded-For", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "X-Real-IP" };

    private static final Pattern INNER_IP_ADDR_RE = Pattern
            .compile("(10\\.)|(192\\.168\\.)");

    /**
     * 是否局域网ip地址
     * 
     * @param ip
     * @return true 是，false 否
     */
    public static boolean isInnerIpAddr(String ip) {
        Matcher ipmatcher = INNER_IP_ADDR_RE.matcher(ip);
        return ipmatcher.find();
    }

    /**
     * 获取客户端真实ip
     * 
     * @param req
     * @return
     */
    public static String getClientIP(HttpServletRequest req) {
        String ip = "";
        for (int i = 0; i < PROXY_REMOTE_IP_ADDRESS.length; ++i) {
            ip = req.getHeader(PROXY_REMOTE_IP_ADDRESS[i]);
            if (StringUtils.isEmpty(ip) || ip.equalsIgnoreCase("unknown"))
                continue;
            ip = getRemoteIpFromForward(ip.trim()).trim();
            if (!isInnerIpAddr(ip)) {
                return ip;
            }
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }

    
    /**
     * 目前测试和生产环境下，都是通过F5的"X-Forwarded-For"直接获取源IP
     */
    public static String getClientIP_Prod(HttpServletRequest req){
        String ips = req.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ips)){
            return "";
        }
        
        return getRemoteIpFromForward(ips);
    }
    
    
    /**
     * <p>
     * 从 HTTP Header 中截取客户端连接 IP 地址。如果经过多次反向代理， 在请求头中获得的是以“,”分隔 IP 地址链，第一段为客户端 IP 地址。
     * </p>
     * 
     * @param xforwardIp
     *            从 HTTP 请求头中获取转发过来的 IP 地址链
     * @return 客户端源 IP 地址
     */
    private static String getRemoteIpFromForward(String xforwardIp) {
        int commaOffset = xforwardIp.indexOf(',');
        if (commaOffset < 0) {
            return xforwardIp.trim();
        }
        return xforwardIp.substring(0, commaOffset).trim();
    }
    
//    public static void main(String[] args) {
//        String ips = " 111.5.196.27, 112.5.196.27 , 10.181.8.35   ";
//        System.out.println(getRemoteIpFromForward(ips));
//        RemoteIpUtil.isInnerIpAddr("10.181.2.2");
//    }
}