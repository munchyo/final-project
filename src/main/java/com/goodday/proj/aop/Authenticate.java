package com.goodday.proj.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class Authenticate {

    @Before("execution(* com.goodday.proj.api.member..*(..))")
    public void testAop() {
        log.debug("come in member!!!!");
        log.debug("come in member!!!!");
        log.debug("come in member!!!!");
        log.debug("come in member!!!!");
        log.debug("come in member!!!!");
        log.debug("come in member!!!!");
    }
}
