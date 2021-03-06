package com.demo.common.aspect;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.demo.common.dto.Result;
import com.demo.common.exception.BizException;

@ControllerAdvice
public class RStoneExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RStoneExceptionHandler.class);

//    @ExceptionHandler(BizException.class)
//    public ModelAndView bizExceptionHandler(final HttpServletRequest req, final BizException ex) {
//
//        LOG.warn("handler BizException={}, reqURL={}", ex, req.getRequestURL());
//        Map<String, Result> resultMap = createErrorResult(ex.getErrorCode(), ex.getErrorMsg());
//        return new ModelAndView(new MappingJackson2JsonView(), resultMap);
//    }

    /**
     * 拦截其他系统未知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ModelAndView allExceptionHandler(final HttpServletRequest req, final Throwable ex) {

        LOG.debug("in RStoneExceptionHandler.allExceptionHandler, e={}", ex);
        Map<String, Result> resultMap = null;
        if (ex instanceof BizException){
            BizException bz = (BizException)ex;
            resultMap = createErrorResult(bz.getErrorCode(), bz.getErrorMsg());
        }
       
        return new ModelAndView(new MappingJackson2JsonView(), resultMap);
    }
    
    /**
     * 返回错误码
     *
     * @param errorCode
     *            错误码
     * @param errorMsg
     *            错误信息描述
     * @return Map<String,String>[返回类型说明]
     */
    private Map<String, Result> createErrorResult(final String errorCode, final String errorMsg) {

        Map<String, Result> resultMap = new HashMap<>(1);
        resultMap.put("result", new Result(errorCode, errorMsg));
        return resultMap;
    }

}