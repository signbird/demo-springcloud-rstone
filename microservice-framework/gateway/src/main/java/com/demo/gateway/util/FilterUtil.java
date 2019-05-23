package com.demo.gateway.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.context.RequestContext;

/**
 * 过滤器工具类
 */
public abstract class FilterUtil {

    private static final Logger LOG = LoggerFactory.getLogger(FilterUtil.class);
    
    public static void startFilter(RequestContext ctx) {
        if (!ctx.containsKey("startTime")){
            ctx.set("startTime", System.currentTimeMillis());
        }
    }
    
    public static void endFilter(RequestContext ctx, HttpServletRequest request, String filterName) {
        boolean isContinue = ctx.containsKey("continue") ? (boolean) ctx.get("continue") : true;
        if (!isContinue && ctx.containsKey("startTime")) {
            LOG.debug("end in {}, Gateway-Request URL={}, costs={}ms", filterName, request.getRequestURL(),
                    (System.currentTimeMillis() - (long) ctx.get("startTime")));
        }
    }
    
    /**
     * 重设Response
     *
     * @param ctx
     *            上下文RequestContext
     * @param isSend
     *            是否转发请求
     * @param code
     *            Response响应状态码
     */
//    public static void setZuulResponse(final RequestContext ctx, final int code, final String msg) {
//        String body = JSON.toJSONString(createResult(code + "", msg));
//        
//        ctx.set("continue", false); 
//        ctx.setSendZuulResponse(false);
//        ctx.setResponseStatusCode(code);
//        ctx.setResponseBody(body);
//    }

    /**
     * 将request body转换成string
     *
     * @param request
     * @return java.lang.String
     */
    public static String getReqBody(final HttpServletRequest request) {
        String content = "";
        InputStream is = null;
        try {
            is = request.getInputStream();
            content = IOUtils.toString(is);
            is.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return content;
    }
    
    /**
     * 创建返回对象
     *
     * @param resultCode
     *            返回码
     * @param resultMsg
     *            返回信息
     * @return resultMap
     */
//    private static Map<String, Result> createResult(final String resultCode, final String resultMsg) {
//        Map<String, Result> resultMap = new HashMap<>(1);
//        resultMap.put("result", new Result(resultCode, resultMsg));
//        return resultMap;
//    }

}
