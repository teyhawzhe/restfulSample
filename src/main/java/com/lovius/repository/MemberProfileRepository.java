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
	
	@Update({"<script>",
		"UPDATE MEMBER_PROFILE",
		"<trim prefix='SET' suffixOverrides=',' >",
			"<if test='name != null' > NAME = #{name} , </if>",
			"<if test='nickName != null' > NICK_NAME = #{nickName} , </if>",
			"<if test='sex != null' > SEX = #{sex} , </if>",
			"<if test='age != null' > AGE = #{age} , </if>",
			"<if test='tel != null' > TEL = #{tel} , </if>",
			"<if test='address != null' > ADDRESS = #{address} , </if>",
		"</trim>",
		"WHERE ID = #{id} ",
	"</script>"})
	public void updateDynamic(MemberProfile memberProfile);
	
}
