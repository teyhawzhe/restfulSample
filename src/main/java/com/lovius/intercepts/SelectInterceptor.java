package com.lovius.intercepts;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
@Slf4j
public class SelectInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		if (invocation.getTarget() instanceof StatementHandler) {
			StatementHandler delegate = (StatementHandler) invocation.getTarget();
			// 取得sql 物件
			BoundSql boundSql = delegate.getBoundSql();
			// 取得sql 字串
			String sql = boundSql.getSql();
			// 取得param 字串
			Object parameterObject = boundSql.getParameterObject();

			// 因為LIST 有相同為__frch_變數_N 所以採用過濾取出真正的變數
			Set<ParamType> paramKey = new LinkedHashSet<>();
			for (ParameterMapping index : boundSql.getParameterMappings()) {
				// IN 特別處理
				if (index.getProperty().startsWith("__frch_")) {
					String param = index.getProperty().replaceAll("__frch_", "");
					param = param.substring(0, param.lastIndexOf("_"));
					ParamType paramType = new ParamType();
					paramType.setParam(param);
					paramType.setType(null);
					paramKey.add(paramType);
				} else if (index.getProperty().indexOf(".") > 0) {
					ParamType paramType = new ParamType();
					paramType.setParam(index.getProperty().split("\\.")[1]);
					paramType.setType("CLAZZ");
					paramKey.add(paramType);
				} else {
					ParamType paramType = new ParamType();
					paramType.setParam(index.getProperty());
					paramType.setType(null);
					paramKey.add(paramType);
				}
			}

			log.info("paramKey size " + paramKey.size());
			log.info("param key " + paramKey);

			// 取得?的value
			ParamMap<Object> paramMap = (ParamMap<Object>) parameterObject;

			log.info("paramMap " + paramMap);

			List<Object> value = new ArrayList<>();

			for (ParamType index : paramKey) {

				if (null == index.getType()) {
					Object valueMap = paramMap.get(index.getParam());
					if (valueMap instanceof List) {
						List<Object> valueList = (List<Object>) valueMap;
						for (Object subIndex : valueList) {
							value.add(subIndex);
						}
					} else if (valueMap instanceof String) {
						value.add(valueMap);
					} else if (valueMap instanceof Number) {
						value.add(valueMap);
					} else {
						log.info(".......");
					}
				} else {
					Object valueMap = paramMap.get("param");
					Field[] declaredFields = valueMap.getClass().getDeclaredFields();
					for (Field field : declaredFields) {
						field.setAccessible(true);
						if (index.getParam().equals(field.getName())) {
							if((field.get(valueMap) instanceof Integer)) {
								value.add(Integer.valueOf(String.valueOf(field.get(valueMap))));
							}else if((field.get(valueMap) instanceof String)) {
								value.add(String.valueOf(field.get(valueMap)));
							}
							
						}
					}

				}

			}

			for (Object index : value) {
				if (index instanceof Number) {
					sql = sql.replaceFirst("\\?", String.valueOf(index));
				} else {
					sql = sql.replaceFirst("\\?", "'" + index + "'");
				}
			}

			// log.info("value "+value);

			// 組合好的sql
			log.info("merge Sql " + sql);
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
