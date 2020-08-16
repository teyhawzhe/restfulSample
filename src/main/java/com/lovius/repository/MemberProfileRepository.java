package com.lovius.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lovius.model.MemberProfile;

@Mapper
public interface MemberProfileRepository {

	@Select("SELECT * FROM MEMBER_PROFILE ORDER BY ID")
	public List<MemberProfile> findAllOrderById();
	
	@Update("UPDATE MEMBER_PROFILE SET AGE = #{param.age} WHERE ID = #{param.id}")
	public void update(@Param("param") MemberProfile memberProfile);
	
}
