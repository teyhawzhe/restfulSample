package com.lovius.common;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lovius.common.exception.DeleteRollBackException;
import com.lovius.common.exception.InsertRollBackException;
import com.lovius.common.exception.UpdateRollBackException;
import com.lovius.model.SysLog;
import com.lovius.service.interfaces.SysLogService;
import com.lovius.utils.CmDateUtils;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private HttpServletRequest request;

	public void saveLog(String methodName , String exception) {
		try {
			String seq = (String) request.getAttribute("ME-SEQNO");
			String lvl = (String) request.getAttribute("Lvl");

			SysLog sysLog = new SysLog();
			sysLog.setSeqNo(seq);

			if (null == lvl) {
				lvl = "1";
				request.setAttribute("Lvl", lvl);
				sysLog.setLvl(Integer.valueOf(lvl));
			} else {
				lvl = String.valueOf(Integer.valueOf(lvl) + 1);
				request.setAttribute("Lvl", lvl);
				sysLog.setLvl(Integer.valueOf(lvl));
			}

			sysLog.setSysDate(CmDateUtils.currentYYYYMMDD());
			sysLog.setSysTime(CmDateUtils.currentHHMMSS());
			sysLog.setApi(request.getRequestURI());
			sysLog.setClassName("com.lovius.common");
			sysLog.setClassMethod(methodName);
			sysLog.setUri(request.getRemoteAddr());
			sysLog.setAp(request.getContextPath());
			sysLog.setError(exception);
			sysLogService.insert(sysLog);
		} catch (Exception ex) {

		}
	}

	@ExceptionHandler(value = Exception.class)
	public RMessage exceptionHandle(Exception ex) {
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		saveLog("exceptionHandle",stack.toString());
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.exception);
	}

	@ExceptionHandler(value = UpdateRollBackException.class)
	public RMessage updateRollBackExceptionHandle(UpdateRollBackException ex) {
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		saveLog("updateRollBackExceptionHandle",stack.toString());
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.updateFailed);
	}

	@ExceptionHandler(value = InsertRollBackException.class)
	public RMessage insertRollBackExceptionHandle(InsertRollBackException ex) {
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		saveLog("insertRollBackExceptionHandle",stack.toString());
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.insertFailed);
	}

	@ExceptionHandler(value = DeleteRollBackException.class)
	public RMessage deleteRollBackExceptionHandle(DeleteRollBackException ex) {
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		saveLog("deleteRollBackExceptionHandle",stack.toString());
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.deleteFailed);
	}

}
