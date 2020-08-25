package com.lovius.intercepts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lovius.utils.CmDateUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Intercepts({
		@Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class, Integer.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
@Slf4j
public class SqlInterceptor implements Interceptor {

	@Autowired
	private DataSource datsSource;

	@Autowired
	private HttpServletRequest request;

	private final String exclude= "SYS_LOG";
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		if (invocation.getTarget() instanceof StatementHandler) {
			String sql = genSql(invocation);
			if(!sql.contains(exclude)) {
				recordLog(sql);
			}
		}
		return invocation.proceed();

	}

	private void recordLog(String sql) throws SQLException {

		if (null != request.getAttribute("ME-SEQNO")) {

			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO SYS_LOG ");
			sb.append("(SEQNO, LEVEL, API, CLASS_NAME, CLASS_FUNCTION, IN_ARGS, OUT_ARGS, SYS_DATE, SYS_TIME) ");
			sb.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			Connection con = datsSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setString(1, (String) request.getAttribute("ME-SEQNO"));
			ps.setInt(2, 3);
			ps.setString(3, request.getRequestURI());
			ps.setString(4, "com.lovius.intercepts.SqlInterceptor");
			ps.setString(5, "intercept");
			ps.setString(6, sql);
			ps.setString(7, null);
			ps.setString(8, CmDateUtils.currentYYYYMMDD());
			ps.setString(9, CmDateUtils.currentHHMMSSSSS());
			ps.execute();
		}
	}

	public String genSql(Invocation invocation) {
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

		Map<String, Object> parameterObject = (Map<String, Object>) boundSql.getParameterObject();
		for (String index : paramKey) {
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
		log.info("Sql merge->" + sql);
		log.info("----------------------------------------------------------");
		return sql;
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
