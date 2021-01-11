package com.nhsoft.lemon.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author wanglei
 */
@Aspect
@Component
@Slf4j
public class Log {


    @After("execution(* com.nhsoft.lemon.controller..*.*(..))")
    public Object around(ProceedingJoinPoint point) {
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        log.info(methodName + "方法开始执行,参数为：" + args.toString());
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info(methodName+"方法执行结束,返回值为：" + proceed);
        return proceed;
    }
}
