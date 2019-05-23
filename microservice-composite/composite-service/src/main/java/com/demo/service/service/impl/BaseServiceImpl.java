package com.demo.service.service.impl;

import com.demo.common.dto.Result;
import com.demo.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 调用远程方法输出日志，并判断结果，如果失败则抛出异常.
     *
     * @param service
     *            服务名
     * @param method
     *            远程方法名
     * @param result
     *            结果
     * @deprecated 在切面中统一处理，该方法暂时废弃
     */
    @Deprecated 
    protected void callRemoteServiceResult(final String service, final String method, final Result result)
            throws BizException {

        if (result == null) {
            LOG.warn("error when invoke {}.{}, result is null", service, method);
            StringBuilder sb = new StringBuilder();
            sb.append(service).append(".").append(method).append(" Result is null");
            throw new BizException("8000000009", sb.toString());
        }
        
        if (!result.getResultCode().endsWith("00000")) {
            LOG.warn("error when invoke {}.{}, result is {}:{}", service, method, result.getResultCode(),
                    result.getResultMessage());
            throw new BizException(result.getResultCode(), result.getResultMessage());
        }
    }
}