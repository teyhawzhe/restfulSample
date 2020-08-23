package com.lovius.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lovius.common.RMessage;
import com.lovius.common.log.repository.LogSeqRepository;
import com.lovius.common.log.service.interfaces.SysLogService;
import com.lovius.model.SysLog;
import com.lovius.model.SysLogPk;
import com.lovius.utils.DataUtils;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private LogSeqRepository logSeqRepository;
	
	@Autowired
	private SysLogService sysLogService;
	
	@Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMappingBefore(JoinPoint joinPoint) throws Exception {
		combineLog(joinPoint,0);
	}

	@AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.GetMapping)", returning = "result")
	public void getMappingAfterReturn(JoinPoint joinPoint, Object result) throws Exception {
		combineLog(joinPoint,6,result);
	}

	@Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMappingBefore(JoinPoint joinPoint) throws Exception {
		combineLog(joinPoint,0);
	}

	@AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.PostMapping)", returning = "result")
	public void postMappingAfterReturn(JoinPoint joinPoint, Object result) throws Exception {
		combineLog(joinPoint,6,result);
	}

	@Before("@annotation(org.springframework.web.bind.annotation.PutMapping)")
	public void putMappingBefore(JoinPoint joinPoint) throws Exception {
		combineLog(joinPoint,0);
	}

	@AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.PutMapping)", returning = "result")
	public void putMappingAfterReturn(JoinPoint joinPoint, Object result) throws Exception {
		combineLog(joinPoint,6,result);
	}

	@Before("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public void deleteMappingBefore(JoinPoint joinPoint) throws Exception {
		combineLog(joinPoint,0);
	}

	@AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.DeleteMapping)", returning = "result")
	public void deleteMappingAfterReturn(JoinPoint joinPoint, Object result) throws Exception {
		combineLog(joinPoint,6,result);
	}

	@Before("execution(* com.lovius.service..*(..))")
	public void serviceBefore(JoinPoint joinPoint) throws Exception {
		combineLog(joinPoint,1);
	}
	
	@AfterReturning(pointcut = "execution(* com.lovius.service..*(..))", returning = "result")
	public void serviceAfterReturn(JoinPoint joinPoint, Object result) throws Exception {
		combineLog(joinPoint,5,result);
	}
	
	@Before("execution(* com.lovius.repository..*(..)) ")
	public void repositorybefore(JoinPoint joinPoint) throws Exception {
		combineLog(joinPoint,2);
	}
	
	@AfterReturning(pointcut = "execution(* com.lovius.repository..*(..))", returning = "result")
	public void repositoryAfterReturn(JoinPoint joinPoint, Object result) throws Exception {
		combineLog(joinPoint,4,result);
	}
	
	/*@Before("execution(* com.lovius.intercepts..*(..)) ")
	public void sqlInterceptorAfterReturn(JoinPoint joinPoint) throws Exception {
		
		log.info("hhahahahh");
		//combineLog(joinPoint,4,result);
	}*/
	
	public void combineLog(JoinPoint joinPoint,int level) throws Exception {
		combineLog(joinPoint,level, null);
	}
	
	//組合要insert到DB
	public void combineLog(JoinPoint joinPoint,int level, Object result) throws Exception {
		//取得API執行序號
		String seq = (String) request.getAttribute("ME-SEQNO");
		SysLog sysLog =  new SysLog();
		SysLogPk sysLogPk = new SysLogPk();
		if(null != seq) {
			sysLogPk.setSeqno(seq);
		}else {
			sysLogPk.setSeqno(DataUtils.currentYYYYMMDD()+StringUtils.leftPad(String.valueOf(logSeqRepository.getLogSeq()), 10, '0'));
			request.setAttribute("ME-SEQNO", sysLogPk.getSeqno());
		}
		
		sysLogPk.setLevel(level);
		sysLogPk.setSysDate(DataUtils.currentYYYYMMDD());
		sysLogPk.setSysTime(DataUtils.currentHHMMSSSSS());
		sysLog.setSysLogPk(sysLogPk);
		sysLog.setApi("request.getRequestURI()");
		sysLog.setClassName(joinPoint.getSignature().getDeclaringTypeName());
		sysLog.setClassFunction(joinPoint.getSignature().getName());
		Object[] signatureArgs = joinPoint.getArgs();
		StringBuffer sb = new StringBuffer();
		for (Object signatureArg : signatureArgs) {
			sb.append(signatureArg);
		}
		sysLog.setInArgs(sb.toString());
		
		if(null != result) {
			//RMessage rm = (RMessage) result;
			sysLog.setOutArgs(result.toString());
		}
		sysLogService.insert(sysLog);
	}
	
}
