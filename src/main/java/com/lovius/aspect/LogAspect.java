package com.lovius.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lovius.model.SysLog;
import com.lovius.service.interfaces.SeqService;
import com.lovius.service.interfaces.SysLogService;
import com.lovius.utils.CmDateUtils;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private SeqService seqService;

	@Autowired
	private SysLogService sysLogService;

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMappingJointPoint() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMappingJointPoint() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
	public void putMappingJointPoint() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public void deleteMappingJointPoint() {
	}

	@Pointcut("execution(* com.lovius.service..*(..))")
	public void serviceJointPoint() {
	}

	@Pointcut("!execution(* com.lovius.service.SysLogServiceImpl.*(..)) && !execution(* com.lovius.service.SeqServiceImpl.*(..))")
	public void excludeServiceJointPoint() {
	}

	@Pointcut("execution(* com.lovius.repository..*(..))")
	public void repositoryJointPoint() {
	}

	@Pointcut("!execution(* com.lovius.repository.SeqRepository.*(..)) && !execution(* com.lovius.repository.SysLogRepository.*(..))")
	public void excludeRepositoryJointPoint() {
	}

	@Before("getMappingJointPoint() || postMappingJointPoint() || putMappingJointPoint() || deleteMappingJointPoint() ||  (serviceJointPoint() && excludeServiceJointPoint()) || (repositoryJointPoint() && excludeRepositoryJointPoint())")
	public void before(JoinPoint joinPoint) throws Exception {
		combineLog(joinPoint);
	}
	
	@AfterReturning(pointcut = "getMappingJointPoint() || postMappingJointPoint() || putMappingJointPoint() || deleteMappingJointPoint() ||  (serviceJointPoint() && excludeServiceJointPoint()) || (repositoryJointPoint() && excludeRepositoryJointPoint())", returning = "result")
	public void afterReturn(JoinPoint joinPoint, Object result) throws Exception {
		combineLog(joinPoint, result);
	}

	public void combineLog(JoinPoint joinPoint) throws Exception {
		combineLog(joinPoint, null);
	}

	// 組合要insert到DB
	public void combineLog(JoinPoint joinPoint, Object result) throws Exception {
		try {
			// 取得API執行序號
			String seq = (String) request.getAttribute("ME-SEQNO");
			
			String lvl =  (String) request.getAttribute("Lvl");
			
			SysLog sysLog = new SysLog();
			if (null != seq) {
				sysLog.setSeqNo(seq);
			} else {
				sysLog.setSeqNo(CmDateUtils.currentYYYYMMDD()
						+ StringUtils.leftPad(String.valueOf(seqService.getLogSeq()), 10, '0'));
				request.setAttribute("ME-SEQNO", sysLog.getSeqNo());
			}
			if(null == lvl) {
				lvl = "1";
				request.setAttribute("Lvl", lvl);
				sysLog.setLvl(Integer.valueOf(lvl));
			}else {
				lvl =  String.valueOf( Integer.valueOf(lvl) + 1 );
				request.setAttribute("Lvl", lvl);
				sysLog.setLvl(Integer.valueOf(lvl));
			}
			
			sysLog.setSysDate(CmDateUtils.currentYYYYMMDD());
			sysLog.setSysTime(CmDateUtils.currentHHMMSS());
			sysLog.setApi(request.getRequestURI());
			sysLog.setClassName(joinPoint.getSignature().getDeclaringTypeName());
			sysLog.setClassMethod(joinPoint.getSignature().getName());
			sysLog.setUri(request.getRemoteAddr());
			sysLog.setAp(request.getContextPath());
			Object[] signatureArgs = joinPoint.getArgs();
			StringBuffer sb = new StringBuffer();
			for (Object signatureArg : signatureArgs) {
				sb.append(signatureArg);
			}
			
			if(sb.length()>0) {
				sysLog.setInArgs(sb.toString());
			}
			
			if (null != result) {
				sysLog.setOutArgs(result.toString());
			}
			sysLogService.insert(sysLog);
		} catch (Exception ex) {
			log.info("exex");
		}
	}

}
