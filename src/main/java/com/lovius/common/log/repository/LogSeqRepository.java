package com.lovius.common.log.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LogSeqRepository {

	@Select("SELECT log_seq.nextval FROM DUAL")
	public Long getLogSeq();
	
}
