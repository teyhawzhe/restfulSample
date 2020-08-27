package com.lovius.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogRepository{

	@Insert("INSERT INTO SYS_LOG "
			+ "(SEQ_NO, LVL, API, CLASS_NAME, CLASS_METHOD, IN_ARGS, OUT_ARGS, SQL_TEXT, ERROR, SYS_DATE, SYS_TIME, URI , AP) "
			+ "VALUES(#{SEQ_NO}, #{LVL}, #{API}, #{CLASS_NAME}, #{CLASS_METHOD}, #{IN_ARGS}, #{OUT_ARGS}, #{SQL_TEXT}, #{ERROR}, #{SYS_DATE}, #{SYS_TIME}, #{URI}, #{AP})")
	public void insert(Map<String, Object> params) throws Exception ;

	public void update(Map<String, Object> params) throws Exception ;

	public void delete(Map<String, Object> params) throws Exception ;


	 
	
}
