package com.lovius.common.log.service.interfaces;

import com.lovius.model.SysLog;
import com.lovius.service.interfaces.ServicePattern;

public interface SysLogService {
	public void insert(SysLog param) throws Exception;
}
