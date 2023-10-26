package com.project.pppreclamationmicroservice.Config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Component
@Aspect
@Slf4j
public class LoggingAspect {


    @After("execution(* com.project.pppreclamationmicroservice.services.ClaimService.*(..))")
    public void logMethodAfterReturning(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        log.info("Success method" +' ' + name);
    }
}
