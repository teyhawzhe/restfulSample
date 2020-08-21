package com.lovius.common.log.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogRepository{

	@Insert("INSERT INTO PUBLIC.SYS_LOG "
			+ "(SEQNO, LEVEL, API, CLASS_NAME, CLASS_FUNCTION, IN_ARGS, OUT_ARGS, SYS_DATE, SYS_TIME) "
			+ "VALUES(#{SEQNO}, #{LEVEL}, #{API}, #{CLASS_NAME}, #{CLASS_FUNCTION}, #{IN_ARGS}, #{OUT_ARGS}, #{SYS_DATE}, #{SYS_TIME})")
	public void insert(Map<String, Object> params) throws Exception ;

	public void update(Map<String, Object> params) throws Exception ;

	public void delete(Map<String, Object> params) throws Exception ;


	 
	
}
