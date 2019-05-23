package com.demo.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import com.demo.common.exception.BizException;

/**
 * 请求日志切面类
 */
@Aspect
@Component
public class LogAspect extends BaseAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义一个切点.
     */
    @Pointcut("execution(public * com.migu.rstone.controller.*.*(..))")
    public void controller() {
    }

    /**
     * 定义一个切点.
     */
    @Pointcut("execution(public * com.migu.rstone.client.*.*(..))")
    public void client() {
    }

    /**
     * 定义一个切点.
     */
    @Pointcut("execution(public * com.migu.rstone.outclient.*.*(..))")
    public void outclient() {
    }
    
    /**
     * 数据库访问切点.
     */
    @Pointcut("execution(public * com.migu.rstone.dao.*.*(..))")
    public void dao() {
    }

    /**
     * 方法被调用前执行.
     *
     * @param jp a join point
     * @throws Throwable 异常
     */
    @Around("controller()")
    public Object aroundController(final ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();
        String method = jp.getSignature().getName();
        String reqInfo;
        if (null == args || args.length < 1 || null == args[0]) {
            reqInfo = null;
        } else {
            reqInfo = object2string(args[0]);
        }
        LOG.info("[controller]invoke method={}, params={}", method, reqInfo);
        StopWatch clock = new StopWatch();
        clock.start();

        Object result = null;
        try {
            result = jp.proceed();
        } catch (BizException bz) {
            clock.stop();
            LOG.warn("[controller]error invoke method={}, req={}, costs={}ms, BizException={}", method, reqInfo, clock.getTotalTimeMillis(), bz.toString());
            throw bz;
        } catch (Throwable e) {
            clock.stop();
            LOG.error("[controller]error invoke method={}, req={}, costs={}ms, ", method, reqInfo, clock.getTotalTimeMillis(), e);
            throw e;
        }
        
        clock.stop();
        LOG.info("[controller]end invoke method={}, rsp={}, req={}, costs={}ms", method, object2string(result), reqInfo, clock.getTotalTimeMillis());
        return result;
    }

    /**
     * 方法被调用前执行.
     *
     * @param jp a join point
     * @throws Throwable 异常
     */
    @Around("client()")
    public Object aroundClient(final ProceedingJoinPoint jp) throws Throwable {

        Object[] args = jp.getArgs();
        String method = jp.getSignature().getName();

        String clientName ="";
        try {
            clientName = getClientName(jp.getTarget().toString());
        } catch (Throwable e) {
            LOG.warn(e.toString());
        }
        
        String reqInfo = (null == args || args.length < 1 || null == args[0]) ? null : args[0].toString();
        
        LOG.info("invoke client {} method={}, params={}", clientName, method, reqInfo);
        StopWatch clock = new StopWatch();
        clock.start();
        
        Object result = null;
        try {
            result = jp.proceed();
        } catch (BizException bz) {
            clock.stop();
            LOG.warn("error invoke client {} method={}, req={}, costs={}ms, BizException={}", clientName, method, reqInfo, clock.getTotalTimeMillis(), bz.toString());
            throw bz;
        } catch (Throwable e) {
            clock.stop();
            LOG.error("error invoke client {} method={}, req={}, costs={}ms,", clientName, method, reqInfo, clock.getTotalTimeMillis(), e);
            throw e;
        }
        
        clock.stop();
        LOG.info("end invoke client {} method={}, rsp={}, req={}, costs={}ms", clientName, method, result, reqInfo, clock.getTotalTimeMillis());
        return result;
    }

    /**
     * 方法被调用前执行.
     *
     * @param jp a join point
     * @author huadq
     */
    @Around("outclient()")
    public Object aroundOutClient(final ProceedingJoinPoint jp) throws Throwable {
        String method = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        String reqInfo = "";
        if (null == args || args.length < 1 || null == args[0]) {
            reqInfo = null;
        } else {
            for (Object obj : args) {
                if(!StringUtils.isEmpty(obj)){
                    reqInfo += object2string(obj) + " ";
                }
            }
        }

        String clientName ="";
        try {
            clientName = getClientName(jp.getTarget().toString());
        } catch (Throwable e) {
            LOG.warn(e.toString());
        }

        
        LOG.info("invoke client {} method={}, params={}", clientName, method, reqInfo);
        StopWatch clock = new StopWatch();
        clock.start();
        
        Object result = null;
        try {
            result = jp.proceed();
        } catch (BizException bz) {
            clock.stop();
            LOG.warn("error invoke client {} method={}, req={}, costs={}ms, BizException={}", clientName, method, reqInfo, clock.getTotalTimeMillis(), bz.toString());
            throw bz;
        } catch (Throwable e) {
            clock.stop();
            LOG.error("error invoke client {} method={}, req={}, costs={}ms,", clientName, method, reqInfo, clock.getTotalTimeMillis(), e);
            throw e;
        }
        
        clock.stop();
        LOG.info("end invoke client {} method={}, rsp={}, req={}, costs={}ms", clientName, method, result, clock.getTotalTimeMillis(), reqInfo);
        return result;
    }
    
    /**
     * 方法被调用前执行.
     *
     * @param jp a join point
     * @throws Throwable 异常
     */
    @Around("dao()")
    public Object aroundDao(final ProceedingJoinPoint jp) throws Throwable {

        Object[] args = jp.getArgs();
        String method = jp.getSignature().getName();
        String daoName = jp.getSignature().getDeclaringTypeName();
        daoName = daoName.substring(daoName.lastIndexOf(".") + 1);

        // 数据库操作，正常日志只打印debug级别。
        String reqInfo = (null == args || args.length < 1 || null == args[0]) ? null : args[0].toString();
        LOG.debug("invoke dao {} method={}, params={}", daoName, method, reqInfo);
        StopWatch clock = new StopWatch();
        clock.start();
        
        Object result = null;
        try {
            result = jp.proceed();
        } catch (Throwable e) {
            clock.stop();
            LOG.error("error invoke dao {} method={}, req={}, costs={}ms,", daoName, method, reqInfo, clock.getTotalTimeMillis(), e);
            throw e;
        }
        
        clock.stop();
        LOG.debug("end invoke dao {} method={}, rsp={}, req={}, costs={}ms", daoName, method, result, clock.getTotalTimeMillis(), reqInfo);
        return result;
    }

}
