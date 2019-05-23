package com.demo.common.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.common.dto.BaseRsp;
import com.demo.common.util.JaxbUtil;

/**
 */
public abstract class BaseAspect {

    /**
     * 获取URL后缀
     *
     * @return
     */
    protected String getClientUrlSuffix(final ProceedingJoinPoint jp) {
        Method m = ((MethodSignature) jp.getSignature()).getMethod();
        return m.getAnnotation(RequestMapping.class).value()[0];
    }

    /**
     * 获取URL后缀
     *
     * @return
     */
    protected String getUrlSuffix() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String originalUrl = request.getRequestURL().toString();
        return originalUrl.substring(originalUrl.indexOf("/", 10));
    }

    /**
     * 获取client 的服务名
     *
     * @return clientName
     */
    protected String getClientName(String s) {

        String clientName ="";
        if(s.contains("HardCodedTarget")&&s.contains("name")) {
            int i = s.indexOf(",");
            String s1 = s.substring(i + 1);
            String s2 = s1.substring(0, s1.indexOf(","));
            clientName = s2.split("=")[1];
        }
        return clientName;
    }


    /**
     * 请求是否成功
     */
    protected String getResultCode(final Object result) {
        String resultCode = "";
        if (result instanceof BaseRsp) {
            BaseRsp rsp = (BaseRsp) result;
            resultCode = rsp.getResult() == null ? null : rsp.getResult().getResultCode();
        }
        return resultCode;
    }

    /***
     * Object转换String,去掉XML格式的空格
     * @Description: object2string
     * @Param: [obj]
     * @return
     */
    protected String object2string(final Object obj) {
        String reqInfo = null;
        if (!StringUtils.isEmpty(obj)) {
            if (obj instanceof String) {
                String reqString = (String) obj;
                if (reqString.startsWith(JaxbUtil.XML_PREFIX)) {
                    reqInfo = JaxbUtil.removeBlank(reqString);
                } else {
                    reqInfo = reqString;
                }
            } else {
                reqInfo = obj.toString();
            }
        }
        return reqInfo;
    }
}
