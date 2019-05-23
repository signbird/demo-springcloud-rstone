package com.demo.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.gateway.util.FilterUtil;
import com.demo.gateway.util.RemoteIpUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 日志过滤器
 */
@Component
public class LogFilter extends ZuulFilter {
    
    private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);
    
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
    
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String ip = RemoteIpUtil.getClientIP(ctx.getRequest());
        String requestBody = FilterUtil.getReqBody(ctx.getRequest());
        
        LOG.info("in Gateway-Request URL={}, clientIP={}, X-Forwarded-For={}, reqBody={}", ctx.getRequest().getRequestURL(), ip, ctx.getRequest().getHeader("X-Forwarded-For"), requestBody);
        return null;
    }
}