package com.lovius.intercepts;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Intercepts({
		@Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class, Integer.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
@Slf4j
public class SqlInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		if (invocation.getTarget() instanceof StatementHandler) {
			StatementHandler delegate = (StatementHandler) invocation.getTarget();
			// 取得sql 物件
			BoundSql boundSql = delegate.getBoundSql();
			// 取得sql 字串
			String sql = boundSql.getSql();
	
			// 因為LIST 有相同為__frch_變數_N 所以採用過濾取出真正的變數
			Set<String> paramKey = new LinkedHashSet<>();
			for (ParameterMapping index : boundSql.getParameterMappings()) {
				// IN 特別處理
				if (index.getProperty().startsWith("__frch_")) {
					String param = index.getProperty().replaceAll("__frch_", "");
					param = param.substring(0, param.lastIndexOf("_"));
					paramKey.add(param);
				} else {
					paramKey.add(index.getProperty());
				}
			}

			Map<String,Object> parameterObject = (Map<String, Object>) boundSql.getParameterObject();
			log.info(parameterObject.toString());

			for(String index : paramKey) {
				Object value = parameterObject.get(index);
				if (value instanceof List) {
					List<Object> valueList = (List<Object>) value;
					for (Object subIndex : valueList) {
						if (value instanceof Number) {
							sql = sql.replaceFirst("\\?", String.valueOf(subIndex));
						} else {
							sql = sql.replaceFirst("\\?", "'" + subIndex + "'");
						}
					}
				} else if (value instanceof Number) {
					sql = sql.replaceFirst("\\?", String.valueOf(value));
				} else {
					sql = sql.replaceFirst("\\?", "'" + value + "'");
				}
			}
		 
			log.info("----------------------------------------------------------");
			log.info("merge Sql " + sql);
			log.info("----------------------------------------------------------");
		}
		return invocation.proceed();

	}

	/*
	 * @Override public Object plugin(Object target) { return Plugin.wrap(target,
	 * this); }
	 * 
	 * @Override public void setProperties(Properties properties) {
	 * 
	 * }
	 */

}
