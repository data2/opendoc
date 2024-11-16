
package com.data2.opendoc.manager.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * rest请求层拦截器，用来进行header信息注入
 *
 * @author auto-generated
 * @date 2024/11/16
 */
//@Aspect
//@Component
//@Order(0)
public class RestControllerAop  {

    @Pointcut(value = "within(com.data2.opendoc.manager.opendoc.controller..*Controller)")
    public void apiPointcut() {

    }

    @Around(value = "apiPointcut()")
    public Object doRestApi(ProceedingJoinPoint joinPoint) {
        return null;
        //return doRestApi(joinPoint);
    }
}
