package com.lovius.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ClazzToMap {

	public static Map<String, Object> handle(Object obj) throws IllegalArgumentException, IllegalAccessException {

		if (null == obj) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);

			if (field.getName().equals("serialVersionUID")) {
				continue;
			}

			map.put(colTrans(field.getName()), field.get(obj));
		}

		return map;

	}

	public static String colTrans(String colName) {

		if (StringUtils.isBlank(colName)) {
			return null;
		}

		char[] colNameArray = colName.toCharArray();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < colNameArray.length; i++) {
			int charType = Character.getType(colNameArray[i]);

			if (charType == Character.LOWERCASE_LETTER) {
				String upper = String.valueOf(colNameArray[i]).toUpperCase();
				sb.append(upper);
			} else {
				sb.append("_" + colNameArray[i]);
			}
		}

		return sb.toString();

	}

}
