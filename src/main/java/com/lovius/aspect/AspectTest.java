package com.lovius.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lovius.common.RMessage;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AspectTest {

	@Autowired
	private HttpServletRequest request;

	/*
	 * @Before(value =
	 * "execution(* com.lovius.service.MemberProfileServiceImpl.*(..)) ") public
	 * void test(JoinPoint joinPoint) {
	 * System.out.println("12312312333333333333333"); }
	 * 
	 * @Before("pointcut()") public void test1(JoinPoint joinPoint) {
	 * System.out.println("11111111111"); }
	 * 
	 * @Pointcut(value =
	 * "execution(* com.lovius.rest.controller.MemberProfileRestController.*(..)) ")
	 * public void pointcut() { System.out.println("pointcut"); }
	 * 
	 * @After("execution(* com.lovius.rest.controller.*.*(..)) ") public void
	 * adter(JoinPoint joinPoint) { System.out.println("adter"); }
	 */

	/*
	 * @After("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	 * public void getMappingAfterJoinPoint(JoinPoint joinPoint) {
	 * log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
	 * log.info("method name = " + joinPoint.getSignature().getName()); }
	 */

	@Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMappingBefore(JoinPoint joinPoint) {
		request.setAttribute("TRACKING", "I am here!");
		log.info("logging get mapping before...");
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
	}

	@AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.GetMapping)", returning = "result")
	public void getMappingAfterReturn(JoinPoint joinPoint, Object result) {
		log.info("logging get mapping after...");
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		log.info("wow , " + request.getAttribute("TRACKING"));
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
		RMessage rm = (RMessage) result;
		log.info("output -> " + rm.toString());
		log.info("===================================");
	}

	@Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMappingBefore(JoinPoint joinPoint) {
		log.info("logging post mapping before...");
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		
		log.info("request " + request.getRemoteAddr());
		
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
	}

	@AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.PostMapping)", returning = "result")
	public void postMappingAfterReturn(JoinPoint joinPoint, Object result) {
		log.info("logging post mapping after...");
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
		RMessage rm = (RMessage) result;
		log.info("output -> " + rm.toString());
		log.info("===================================");
	}

	@Before("@annotation(org.springframework.web.bind.annotation.PutMapping)")
	public void putMappingBefore(JoinPoint joinPoint) {
		log.info("logging put mapping before...");
		log.info("Thread id = "+Thread.currentThread().getId());
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		log.info("request ip = " + request.getRemoteAddr());
		log.info("request method = " + request.getMethod());
		log.info("request ip = " + request.getRequestURL());
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
	}

	@AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.PutMapping)", returning = "result")
	public void putMappingAfterReturn(JoinPoint joinPoint, Object result) {
		log.info("logging put mapping after...");
		log.info("Thread id = "+Thread.currentThread().getId());
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
		RMessage rm = (RMessage) result;
		log.info("output -> " + rm.toString());
		log.info("===================================");
	}

	@Before("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public void deleteMappingBefore(JoinPoint joinPoint) {
		log.info("logging delete mapping before...");
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
	}

	@AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.DeleteMapping)", returning = "result")
	public void deleteMappingAfterReturn(JoinPoint joinPoint, Object result) {
		log.info("logging delete mapping after...");
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
		RMessage rm = (RMessage) result;
		log.info("output -> " + rm.toString());
		log.info("===================================");
	}

	@Before("execution(* com.lovius.service..*(..))")
	public void serviceBefore(JoinPoint joinPoint) {
		log.info("logging service before...");
	}
	
	@AfterReturning(pointcut = "execution(* com.lovius.service..*(..))", returning = "result")
	public void serviceAfterReturn(JoinPoint joinPoint, Object result) {
		log.info("logging service after...");
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
		log.info("output -> " + result.toString());
		log.info("===================================");
	}
	
	@Before("execution(* com.lovius.repository..*(..))")
	public void repositorybefore(JoinPoint joinPoint) {
		log.info("logging repository before...");
	}
	
	@AfterReturning(pointcut = "execution(* com.lovius.repository..*(..))", returning = "result")
	public void repositoryAfterReturn(JoinPoint joinPoint, Object result) {
		log.info("logging repository after...");
		log.info("class name = " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("method name = " + joinPoint.getSignature().getName());
		Object[] signatureArgs = joinPoint.getArgs();
		log.info("input->");
		for (Object signatureArg : signatureArgs) {
			log.info("param : " + signatureArg);
		}
		log.info("output -> " + result.toString());
		log.info("===================================");
	}
}
