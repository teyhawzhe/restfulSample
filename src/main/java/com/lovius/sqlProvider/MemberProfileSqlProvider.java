package com.lovius.sqlProvider;

import java.util.Map;

public class MemberProfileSqlProvider {

	public String deleteSql = "DELETE MEMBER_PROFILE";
	
	public String delete(Map<String,Object> param) {
		if(null != param.get("ID")) {
			deleteSql = deleteSql + " WHERE ID = '" + param.get("ID")+"'";
		}
		
		return deleteSql;
	}
	
}
