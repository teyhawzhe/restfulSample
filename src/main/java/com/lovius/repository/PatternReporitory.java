package com.lovius.repository;

import java.util.Map;

public interface PatternReporitory {
	
	public void insert(Map<String,Object> params) throws Exception;
	
	public void update(Map<String,Object> params) throws Exception;
	
	public void delete(Map<String,Object> params) throws Exception;
		
}
