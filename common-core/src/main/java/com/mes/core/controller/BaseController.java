package com.mes.core.controller;

import java.util.Map;

import com.mes.core.utils.StringUtils;

/**
 * 基本控制器
 * @author Administrator
 *
 */
public class BaseController {
	/**
	 * 处理查询条件IN(已逗号分隔的字符串)
	 * @param condition
	 * @param fieldName
	 */
	public void processWhereIn(Map<String, Object> condition, String fieldName) {
		String str = StringUtils.trim(condition.get(fieldName));
		if (StringUtils.hasText(str)) {
			condition.put(fieldName, str.split(","));
		}
	}
}
