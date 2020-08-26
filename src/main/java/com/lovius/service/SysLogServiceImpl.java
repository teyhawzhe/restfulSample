package com.lovius.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lovius.common.exception.InsertRollBackException;
import com.lovius.model.SysLog;
import com.lovius.repository.SysLogRepository;
import com.lovius.service.interfaces.SysLogService;
import com.lovius.utils.ClazzToMap;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogRepository sysLogRepository;
	
	@Transactional(rollbackFor = InsertRollBackException.class)
	@Override
	public void insert(SysLog param) throws Exception {
		try {
			Map<String,Object> params = ClazzToMap.handle(param);
			params.put("SUB_SEQ", param.getSeqNo());
			sysLogRepository.insert(params);
		}catch (Exception e) {
			throw new InsertRollBackException();
		}
	}

	
}
