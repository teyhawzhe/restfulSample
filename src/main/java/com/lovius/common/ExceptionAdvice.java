package com.lovius.common;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lovius.common.exception.UpdateRollBackException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

	@ExceptionHandler(value = UpdateRollBackException.class)
	public RMessage updateRollBackExceptionHandle(UpdateRollBackException ex) {
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		return new RMessage(HttpStatus.OK.value(), ex.getLocalizedMessage());
	}

}
