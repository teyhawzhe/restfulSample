package com.lovius.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogRepository{

	@Insert("INSERT INTO SYS_LOG "
			+ "(SEQ_NO, LVL, API, CLASS_NAME, CLASS_METHOD, IN_ARGS, OUT_ARGS, SQL_TEXT, ERROR, SYS_DATE, SYS_TIME, URI) "
			+ "VALUES(#{SEQ_NO}, NVL(SELECT max(LVL) FROM SYS_LOG WHERE SEQ_NO =  #{SUB_SEQ} ,0 )+1, #{API}, #{CLASS_NAME}, #{CLASS_METHOD}, #{IN_ARGS}, #{OUT_ARGS}, #{SQL_TEXT}, #{ERROR}, #{SYS_DATE}, #{SYS_TIME}, #{URI})")
	public void insert(Map<String, Object> params) throws Exception ;

	public void update(Map<String, Object> params) throws Exception ;

	public void delete(Map<String, Object> params) throws Exception ;


	 
	
}
