package com.lovius.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SeqRepository {

	@Select("SELECT log_seq.nextval FROM DUAL")
	public Long getLogSeq();
	
}
