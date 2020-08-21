package com.lovius.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lovius.model.MemberProfile;
import com.lovius.sqlProvider.MemberProfileSqlProvider;

@Mapper
public interface MemberProfileRepository {

	@Select("SELECT * FROM MEMBER_PROFILE ORDER BY ID")
	public List<MemberProfile> findAllOrderById();
	
	@Select({"<script>",
		"SELECT * FROM MEMBER_PROFILE",
		"<trim prefix='WHERE' suffixOverrides='AND|OR' >",
		 "<if test='id != null' > ID = #{id} AND </if>",
		 "<if test='hi != null' > ID = #{hi} AND </if>",
		"</trim>",
		"</script>"}
	)
	public MemberProfile findById(String id ,String hi);
	
	@Select({"<script>",
		"SELECT * FROM MEMBER_PROFILE",
		"<trim prefix='WHERE' suffixOverrides='AND|OR' >",
		 "<if test='id != null' > ID IN ",
		 "<foreach collection='id' index='index' item='id' open='(' separator=',' close=')'>",
		 "#{id}",
		 "</foreach>",
		 "AND </if>",
		"</trim>",
		"</script>"}
	)
	public MemberProfile findByIdDymaicIn(Map<String, Object> param);
	
	@Update("UPDATE MEMBER_PROFILE SET AGE = #{AGE} WHERE ID = #{ID}")
	public void update(Map<String,Object> param);
	
	@Update({"<script>",
		"UPDATE MEMBER_PROFILE",
		"<trim prefix='SET' suffixOverrides=',' >",
			"<if test='NAME != null' > NAME = #{NAME} , </if>",
			"<if test='NICK_NAME != null' > NICK_NAME = #{NICK_NAME} , </if>",
			"<if test='SEX != null' > SEX = #{SEX} , </if>",
			"<if test='AGE != null' > AGE = #{AGE} , </if>",
			"<if test='TEL != null' > TEL = #{TEL} , </if>",
			"<if test='ADDRESS != null' > ADDRESS = #{ADDRESS} , </if>",
		"</trim>",
		"WHERE ID = #{ID} ",
	"</script>"})
	public void updateDynamic(Map<String,Object> param);
	
	@Insert("INSERT INTO MEMBER_PROFILE VALUES (#{ID},#{NAME},#{NICK_NAME},#{SEX},#{AGE},#{TEL},#{ADDRESS})")
	public void insert(Map<String,Object> param);
	
	@DeleteProvider(type = MemberProfileSqlProvider.class , method = "delete")
	public void delete(Map<String,Object> param);
}
