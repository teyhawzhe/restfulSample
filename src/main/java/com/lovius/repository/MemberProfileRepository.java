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
	
	@Select("SELECT * FROM MEMBER_PROFILE WHERE ID = #{id} AND Id = #{hi} ")
	public MemberProfile findById(String id ,String hi);
	
	@Select({"<script>",
		"SELECT * FROM MEMBER_PROFILE",
		"<trim prefix='WHERE' suffixOverrides='AND|OR' >",
		 "<if test='id != null' > ID = #{id} AND </if>",
		 "<if test='hi != null' > ID = #{hi} AND </if>",
		"</trim>",
		"</script>"}
	)
	public MemberProfile findByIdDymaic(String id ,String hi);
	
	@Select({"<script>",
		"SELECT * FROM MEMBER_PROFILE",
		"<trim prefix='WHERE' suffixOverrides='AND|OR' >",
		 "<if test='id != null' > ID IN ",
		 "<foreach collection='id' index='index' item='id' open='(' separator=',' close=')'>",
		 "#{id}",
		 "</foreach>",
		 "AND </if>",
		 "<if test='hi != null' > ID IN ",
		 "<foreach collection='hi' index='index' item='hi' open='(' separator=',' close=')'>",
		 "#{hi}",
		 "</foreach>",
		 "AND </if>",
		"</trim>",
		"</script>"}
	)
	public MemberProfile findByIdDymaicIn(@Param("id") List<String> id , @Param("hi") List<String> hi);
	
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
	public void updateDynamic(MemberProfile memberProfile,MemberProfile memberProfile1);
	
	
}
