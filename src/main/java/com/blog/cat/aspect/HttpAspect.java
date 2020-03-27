package com.blog.cat.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用AOP拦截所有Controller并打印请求参数
 *
 * @author: dong
 * @date: 2020/1/3
 */
@Aspect
@Component
public class HttpAspect {

    private static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(* com.blog.cat.controller.*.*(..)) && !execution(* com.blog.cat.controller.BaseController.*(..))" )
    public void httpCut(){

    }

    @Around("httpCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String method = request.getMethod();
        String GET = "get";
        String url = request.getRequestURL().toString();
        String params = "";
        Object[] o = pjp.getArgs();
        String ctrlClassName = pjp.getTarget().getClass().getSimpleName();
        String ctrlMethod = pjp.getSignature().getName();
        if (GET.equals(method)){
            params = request.getQueryString();
        }else {
            params+="[";
            for (Object item: o){
                params += item.toString()+" ";
            }
            params+="]";
        }
        logger.info("ctrlClassName:{}, ctrlMethod:{} ,url:{}, method:{}, params:{} ",ctrlClassName, ctrlMethod,url, method, params);
        Object result = pjp.proceed();
        return result;
    }
}
