package com.lovius.service.interfaces;

import java.util.List;
import java.util.Map;

public interface ServicePattern<T> {
	
	public void insert(T param) throws Exception;
	
	public void update(T param) throws Exception;
	
	public void delete(T param) throws Exception;
	
	public List<T> findALl() throws Exception;
	
	public List<T> findALl(Map<String,Object> param) throws Exception;
	
	public T findById(Map<String,Object> param) throws Exception;
	
}
