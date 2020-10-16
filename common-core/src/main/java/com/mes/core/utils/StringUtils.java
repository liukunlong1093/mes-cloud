package com.mes.core.utils;

import java.util.List;

public class StringUtils {
	/**
	 * 把为null的字符串转换成空白。
	 * 
	 * @param str
	 * @return str
	 */
	public static String nonNull(String str) {
		return (str == null) ? "" : str;
	}

	public static boolean hasText(String str) {
		return !"".equals(nonNull(str));
	}

	/***是否为空***/
	public static boolean hasText(Object obj) {
		String str = trim(obj);
		return !"".equals(nonNull(str));
	}

	/**
	 * 去除字符串头尾的空白,如果是null,则返回空白
	 * 
	 * @param str
	 * @return String
	 */
	public static String trim(String str) {
		return (str == null) ? "" : str.trim();
	}

	/**
	 * 只适用网页上进行字符串null或空白转换成空白 返回&nbsp;,如果有值则返回当前值
	 * 
	 * @param str
	 * @return str
	 */
	public static String webTrim(String str) {
		str = trim(str);
		return str.equals("") ? "&nbsp;" : str;
	}

	/**
	 * 只适用网页上进行对象null或空白转换成空白 返回&nbsp;,如果有值则返回当前值
	 * 
	 * @param obj
	 * @return str
	 */
	public static String webTrim(Object obj) {
		String str = trim(obj);
		return str.equals("") ? "&nbsp;" : str;
	}

	/**
	 * 替换字符串，如果当str没值，则用defult来替换，否则返回当前的str
	 * 
	 * @param str
	 * @param defult
	 * @return str
	 */
	public static String trim(String str, String defult) {
		return (str == null) ? trim(defult) : str.trim();
	}

	/**
	 * 替换对象，如果当obj==null，则用defult来替换，否则返回当前的obj字符串
	 * 
	 * @param str
	 * @param defult
	 * @return
	 */
	public static String trim(Object obj, String defult) {
		return (obj == null) ? trim(defult) : obj.toString().trim();
	}

	/**
	 * 格式化Object对象为字符串，如果为null则返回空白.
	 * 
	 * @param str
	 * @return str
	 */
	public static String trim(Object str) {
		return (str == null) ? "" : str.toString().trim();
	}
	
	/**
	 * 字符串转换成long，转换出错或者为空则返回defaultv
	 * 
	 * @param str
	 * @param defaultv
	 * @return long
	 */
	public static long getAsLong(String str, long defaultv) {
		if (str == null || "".equals(str)) {
			return defaultv;
		}
		try {
			return Long.parseLong(str, 10);
		} catch (Exception e) {
			return defaultv;
		}
	}
	
	/**
	 * 字符串转换成long，转换出错或者为空则返回-1L
	 * 
	 * @param str
	 * @return long
	 */
	public static long getAsLong(String str) {
		return getAsLong(str, -1L);
	}
	
	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：helloWorld->HELLO_WORLD
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成大写
			result.append(name.substring(0, 1).toUpperCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toUpperCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->helloWorld
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将字符串数组转化为已特定分隔符拼接的字符串
	 * @param  arrary 字符串数组
	 * @param  seperator 分隔符
	 * @return 分隔符拼接的字符串
	 */
	public static String join(String arrary[], String seperator) {
		StringBuffer result = new StringBuffer();
		if (arrary == null || arrary.length <= 0) {
			return "";
		}
		for (int i = 0; i < arrary.length; i++) {
			if (arrary[i] != null) {
				result.append(arrary[i].toString());
			}
			if (i != arrary.length - 1) {
				result.append(seperator);
			}
		}
		return result.toString();
	}
	/**
	 * 将集合转化为已特定符号拼接的字符串
	 * @param list 集合
	 * @param seperator 分隔符
	 * @return 分隔符拼接的字符串
	 */
	public static <T> String join(List<T> list, String seperator) {
		StringBuffer result = new StringBuffer();
		if (list == null || list.size() <= 0) {
			return "";
		}
		for (int i = 0; i < list.size(); i++) {
			if(StringUtils.hasText(StringUtils.trim(list.get(i)))){
				result.append(StringUtils.trim(list.get(i)));
			}
			if (i != list.size() - 1) {
				result.append(seperator);
			}
		}
		return result.toString();
	}
}
