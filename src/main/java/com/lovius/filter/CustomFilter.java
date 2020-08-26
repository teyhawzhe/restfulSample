package com.lovius.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lovius.service.interfaces.SeqService;
import com.lovius.utils.CmDateUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@Slf4j
public class CustomFilter extends OncePerRequestFilter {

	@Autowired
	private SeqService seqService;
	
	private final List<String> exludePath = Arrays.asList("/api/swagger-ui.html" , 
			"/api/webjars"
			,"/api/swagger-resources"
			,"/api/v2"
			,"/api/csrf");
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		boolean gate = true;
		for(String index : exludePath) {
			if(request.getRequestURI().startsWith(index)) {
				gate = false;
				break;
			}
		}
		if(gate) {
			log.info(request.getRequestURI());
			request.setAttribute("ME-SEQNO", CmDateUtils.currentYYYYMMDD()
						+ StringUtils.leftPad(String.valueOf(seqService.getLogSeq()), 10, '0'));
		}
		filterChain.doFilter(request, response);
	}

}
