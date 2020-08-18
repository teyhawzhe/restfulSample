package com.lovius.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@Order(-5)
public class LogAspect {

	@Pointcut("within(com.lovius.rest.controller..*)")
	public void pointCut() {
		System.out.println("......................");
		log.debug("pointCut()");
	}

	@Before("pointCut()")
	public void before(JoinPoint joinPoint) {
		log.debug("start");
	}
	
	@After("pointCut()")
	public void after(JoinPoint joinPoint) {
		log.debug("after");
	}
}
