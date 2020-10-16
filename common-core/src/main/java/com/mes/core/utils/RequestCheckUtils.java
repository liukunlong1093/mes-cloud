package com.mes.core.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mes.core.exception.ApiRuleException;
import com.mes.core.exception.ExceptionEnums;
import com.mes.core.exception.enums.ApiRuleExceptionEnums;
import com.mes.core.pojos.BaseDTO;

/**
  * 项目名称:	[common-core]
  * 包:	        [com.mes.core.utils]    
  * 类名称:		[RequestCheckUtils]  
  * 类描述:		[请求校验参数工具类]
  * 创建人:		[liukl]   
  * 创建时间:	[2017年8月8日 下午5:01:34]   
  * 修改人:		[liukl]   
  * 修改时间:	[2017年8月8日 下午5:01:34]   
  * 修改备注:	[说明本次修改内容]  
  * 版本:		[v1.0]
 */
public class RequestCheckUtils {
	private static final Logger logger = LoggerFactory.getLogger(RequestCheckUtils.class);

	private RequestCheckUtils() {
	}

	/** 是否开启参数验证*/
	public static final boolean CHECK_FLAG = true;

	/** 是否开启开发模式(开发模式会输出详细异常信息.不开启则输用户友好异常)*/
	private static final boolean DEV_MODE = true;
	// 校验配置信息
	private static Map<String, List<Map<String, Map<String, Object>>>> vailateCofig = initVaildatorCofing();

	private static final String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

	public static void throwException(ExceptionEnums exceptionEnums, String message) {
		if (DEV_MODE) {
			throw new ApiRuleException(exceptionEnums, message);
		} else {
			throw new ApiRuleException(exceptionEnums);
		}
	}

	/**
	 * 校验不为空
	 * @param value  值
	 * @param fieldName  字段名称
	 * @throws ApiRuleException  参数规则异常
	 */
	public static void checkNotEmpty(Object value, String fieldName) {
		if (value == null) {
			throwException(ApiRuleExceptionEnums.MISSING_REQUIRED_ARGUMENTS, fieldName + "不能为空.");
		}
		if (((value instanceof String)) && (((String) value).trim().length() == 0))
			throwException(ApiRuleExceptionEnums.MISSING_REQUIRED_ARGUMENTS, fieldName + "不能为空.");
	}

	/**
	 * 校验最大长度
	 * @param value 值
	 * @param maxLength 最大长度
	 * @param fieldName 字段名称
	 * @throws ApiRuleException 参数规则异常
	 */
	public static void checkMaxLength(String value, int maxLength, String fieldName) {
		if ((value != null) && (value.length() > maxLength))
			throwException(ApiRuleExceptionEnums.INVALID_ARGUMENTS, fieldName + "的字符长度不能大于" + maxLength + ".");
	}

	/**
	 * 校验最大集合长度
	 * @param value 值
	 * @param maxSize 最大值
	 * @param fieldName 文件名
	 * @throws ApiRuleException 参数规则异常
	 */
	public static void checkMaxListSize(String value, int maxSize, String fieldName) {
		if (value != null) {
			String[] list = value.split(",");
			if ((list != null) && (list.length > maxSize))
				throwException(ApiRuleExceptionEnums.INVALID_ARGUMENTS, "api-error:Invalid Arguments:the listsize(the string split by \",\") of " + fieldName + " must be less than " + maxSize + ".");
		}
	}

	/**
	 * 校验最大值
	 * @param value  值
	 * @param maxValue  最大值
	 * @param fieldName  字段名称
	 * @throws ApiRuleException 参数规则异常
	 */
	public static void checkMaxValue(Long value, long maxValue, String fieldName) {
		if ((value != null) && (value.longValue() > maxValue))
			throwException(ApiRuleExceptionEnums.INVALID_ARGUMENTS, fieldName + "的数字长度不能大于" + maxValue + ".");
	}

	/**
	 * 校验最小值
	 * @param value  值
	 * @param minValue 最小值
	 * @param fieldName 字段名称
	 * @throws ApiRuleException 参数规则异常
	 */
	public static void checkMinValue(Long value, long minValue, String fieldName) {
		if ((value != null) && (value.longValue() < minValue))
			throwException(ApiRuleExceptionEnums.INVALID_ARGUMENTS, fieldName + "的数字长度不能小于" + minValue + ".");
	}

	/**
	 * 校验最大长度
	 * @param value 值
	 * @param minLength 最小长度
	 * @param fieldName 字段名称
	 * @throws ApiRuleException 参数规则异常
	 */
	public static void checkMinLength(String value, int minLength, String fieldName) {
		if ((value != null) && (value.length() < minLength))
			throwException(ApiRuleExceptionEnums.INVALID_ARGUMENTS, fieldName + "的字符长度不能大于" + minLength + ".");
	}

	/**
	 * 校验范围值
	 * @param value  值
	 * @param minValue 最小值
	 * @param maxValue 最大值
	 * @param fieldName 字段名称
	 * @throws ApiRuleException 参数规则异常
	 */
	public static void checkRangeValue(Long value, long minValue, long maxValue, String fieldName) {
		if ((value != null) && ((value.longValue() < minValue) || (value.longValue() > maxValue)))
			throwException(ApiRuleExceptionEnums.INVALID_ARGUMENTS, fieldName + "的数字范围必须在" + minValue + "和" + maxValue + "之间.");
	}

	/**
	 * 校验范围字符串长度
	 * @param value  值
	 * @param minValue 最小值
	 * @param maxValue 最大值
	 * @param fieldName 字段名称
	 * @throws ApiRuleException 参数规则异常
	 */
	public static void checkRangeLength(String value, long minLength, long maxLength, String fieldName) {
		if ((value != null) && ((value.length() < minLength) || (value.length() > maxLength)))
			throwException(ApiRuleExceptionEnums.INVALID_ARGUMENTS, fieldName + "的字符范围必须在" + minLength + "和" + maxLength + "之间.");
	}

	/**
	 * Validate regular expression.
	 */
	public static void checkRegex(String value, String regExpression, boolean isCaseSensitive, String fieldName) {
		checkNotEmpty(value, fieldName);
		Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression) : Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			throwException(ApiRuleExceptionEnums.INVALID_ARGUMENTS, fieldName + "邮箱格式错误.");
		}

	}

	/**
	 * Validate regular expression.
	 */
	public static void checkEmail(String value, String fieldName) {
		checkRegex(value, emailAddressPattern, false, fieldName);
	}

	public static void vaildator(String op, String field, Object paramVlue) {
		List<Map<String, Map<String, Object>>> vaildatorListMap = vailateCofig.get(op);

		if (vaildatorListMap != null && vaildatorListMap.size() > 0) {
			for (Map<String, Map<String, Object>> vaildatorMap : vaildatorListMap) {
				Map<String, Object> vaildatorFiled = vaildatorMap.get(field);
				if (vaildatorFiled != null && vaildatorFiled.size() > 0) {
					String required = String.valueOf(vaildatorFiled.get("required"));
					if (required != null && !"".equals(required) && "true".equals(required)) {
						checkNotEmpty(paramVlue, field);
						checkValidateType(field, paramVlue, vaildatorFiled);
					} else {
						if (paramVlue != null) {
							checkValidateType(field, paramVlue, vaildatorFiled);
						}
					}
				}
			}

		}

	}

	/**
	 * 校验
	 * @param op
	 * @param args
	 */
	public static void vaildator(String op, Object args[]) {
		if (args != null && args.length > 0) {
			List<Map<String, Map<String, Object>>> vaildatorListMap = vailateCofig.get(op);
			// 如果接口参数与校验参数个数不匹配则忽略当前接口的校验
			if (args.length != vaildatorListMap.size()) {
				logger.warn("当前接口参数与配置文件接口校验参数个数不匹配已自动忽略当前校验规则.");
				return;
			}
			if (vaildatorListMap != null && vaildatorListMap.size() > 0) {
				for (int i = 0, len = vaildatorListMap.size(); i < len; i++) {
					Object arg = args[i];
					Iterator<Entry<String, Map<String, Object>>> ite = vaildatorListMap.get(i).entrySet().iterator();
					while (ite.hasNext()) {
						Entry<String, Map<String, Object>> entry = ite.next();
						Map<String, Object> vaildatorFiled = entry.getValue();
						if (vaildatorFiled != null && vaildatorFiled.size() > 0) {
							String required = String.valueOf(vaildatorFiled.get("required"));
							if (required != null && !"".equals(required) && "true".equals(required)) {
								checkNotEmpty(arg, String.valueOf(vaildatorFiled.get("describe")));
							}
							if (arg instanceof BaseDTO || arg instanceof java.util.Map) {// 复杂类型校验
								vaildatorDTO(arg, vaildatorFiled);

							} else if (arg instanceof java.util.List) {// 集合类型
								vaildatorList(arg, vaildatorFiled);
							} else {// 基础数据类型校验
									// 如果参数设置不能为空进行参数必填校验(spring mvc 默认做此校验)
								checkValidateType(String.valueOf(vaildatorFiled.get("describe")), arg, vaildatorFiled);
							}

						}

					}

				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	private static void vaildatorList(Object arg, Map<String, Object> vaildatorFiled) {
		List<Object> list = (List<Object>) arg;
		if (list != null && list.size() > 0) {
			String type = String.valueOf(vaildatorFiled.get("type"));
			for (Object o : list) {
				if (type.contains("DTO") && type.contains("MAP")) {
					vaildatorDTO(o, vaildatorFiled);
				} else {
					String required = String.valueOf(vaildatorFiled.get("required"));
					if (required != null && !"".equals(required) && "true".equals(required)) {
						checkNotEmpty(o, String.valueOf(vaildatorFiled.get("describe")));
					}
					checkValidateType(String.valueOf(vaildatorFiled.get("describe")), o, vaildatorFiled);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void vaildatorDTO(Object arg, Map<String, Object> vaildatorFiled) {
		JSONObject jsonObj = (JSONObject) JSON.toJSON(arg);
		Object paramsObj = vaildatorFiled.get("params");
		if (paramsObj != null) {
			Map<String, Map<String, Object>> paramsMap = (Map<String, Map<String, Object>>) paramsObj;
			Iterator<Entry<String, Map<String, Object>>> iterator = paramsMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Map<String, Object>> paramEntry = iterator.next();
				String field = paramEntry.getKey();
				Map<String, Object> paramFiled = paramEntry.getValue();
				if (paramFiled != null && paramFiled.size() > 0) {
					String paramRequired = String.valueOf(paramFiled.get("required"));
					if (paramRequired != null && !"".equals(paramRequired) && "true".equals(paramRequired)) {
						checkNotEmpty(jsonObj.get(field), String.valueOf(paramFiled.get("describe")));
					}
					checkValidateType(String.valueOf(paramFiled.get("describe")), jsonObj.get(field), paramFiled);

				}
			}

		}
	}

	/**
	 * 是否包含当前操作的验证配置信息
	 * @param op
	 * @return <code>true</code>包含 <code>false</code> 不包含
	 */
	public static boolean isContainsConfig(String op) {
		boolean flag = false;
		List<Map<String, Map<String, Object>>> vaildatorListMap = vailateCofig.get(op);
		if (vaildatorListMap != null && vaildatorListMap.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 校验参数类型
	 * @param field 字段名称
	 * @param paramVlue 值
	 * @param vaildatorFiled 验证类型
	 */
	private static void checkValidateType(String field, Object paramVlue, Map<String, Object> vaildatorFiled) {
		Object vtypeObj = vaildatorFiled.get("vtype");
		Object typeObj = vaildatorFiled.get("type");
		if (vaildatorFiled == null || vtypeObj == null || typeObj == null || paramVlue != null) {
			return;
		}
		// 验证类型
		String vtypeStr = String.valueOf(vaildatorFiled.get("vtype"));
		// 参数字符类型
		String type = String.valueOf(vaildatorFiled.get("type"));
		assert vtypeStr != null;
		assert type != null;
		if (vtypeStr != null && !"".equals(vtypeStr)) {
			String vtypes[] = vtypeStr.split(";");
			if (vtypes != null && vtypes.length > 0) {
				for (String vtype : vtypes) {
					assert vtype.split(":") != null && vtype.split(":").length >= 0;
					String key = vtype.split(":")[0];
					if ("maxLength".equals(key)) {
						String value = vtype.split(":")[1];
						if ("String".equals(type)) {
							checkMaxLength(StringUtils.trim(paramVlue), Integer.parseInt(value), field);
						} else if ("Long".equals(type)) {
							checkMaxValue((StringUtils.getAsLong(StringUtils.trim(paramVlue, ""))), Long.parseLong(value), field);
						}
					} else if ("minLength".equals(key)) {
						String value = vtype.split(":")[1];
						if ("String".equals(type)) {
							checkMinLength(StringUtils.trim(paramVlue), Integer.parseInt(value), field);
						} else if ("Long".equals(type)) {
							checkMinValue((Long) paramVlue, Long.parseLong(value), field);
						}
					} else if ("email".equals(key)) {
						checkEmail(String.valueOf(paramVlue), field);
					} else if ("rangeLength".equals(key)) {
						String value = vtype.split(":")[1];
						String minValue = value.split(",")[0];
						String maxValue = value.split(",")[1];
						if ("String".equals(type)) {
							checkRangeLength(StringUtils.trim(paramVlue), Integer.parseInt(minValue), Integer.parseInt(maxValue), field);
						} else if ("Long".equals(type)) {
							checkRangeValue((Long) paramVlue, Long.parseLong(minValue), Long.parseLong(maxValue), field);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, List<Map<String, Map<String, Object>>>> initVaildatorCofing() {
		Map<String, List<Map<String, Map<String, Object>>>> map = new HashMap<String, List<Map<String, Map<String, Object>>>>();
		SAXReader sr = new SAXReader();// 获取读取xml的对象。
		try {
			// RequestCheckUtils.class.getResource("/");
			String path = RequestCheckUtils.class.getResource("/") + File.separator + "config" + File.separator + "vaildator.xml";
			Document doc = sr.read(path);
			Element root = doc.getRootElement();// 向外取数据，获取xml的根节点。
			Iterator<Element> talbeIt = root.elementIterator();
			while (talbeIt.hasNext()) {
				Element tb_el = talbeIt.next();
				String op = tb_el.attributeValue("op");
				List<Map<String, Map<String, Object>>> argList = new ArrayList<Map<String, Map<String, Object>>>();
				Iterator<Element> args = (Iterator<Element>) tb_el.elementIterator("arg");
				while (args.hasNext()) {
					Map<String, Map<String, Object>> argMap = new HashMap<String, Map<String, Object>>();
					// 遍历子节点
					Element arg_row = args.next();
					String name = arg_row.attributeValue("name");
					String describe = arg_row.attributeValue("describe");
					String type = arg_row.attributeValue("type");
					String required = arg_row.attributeValue("required");
					String vtype = arg_row.attributeValue("vtype");
					Map<String, Object> fieldMap = new HashMap<String, Object>();
					fieldMap.put("describe", describe);
					fieldMap.put("type", type);
					fieldMap.put("required", required);
					fieldMap.put("vtype", vtype);
					if ("DTO".equals(type) || "Map".equals(type) || type.startsWith("List")) {
						Map<String, Map<String, Object>> paramsMap = new HashMap<String, Map<String, Object>>();
						Iterator<Element> columns = (Iterator<Element>) arg_row.elementIterator("param");
						while (columns.hasNext()) {
							// 遍历子节点
							Element el_row = columns.next();
							String paramName = el_row.attributeValue("name");
							String paramDescribe = el_row.attributeValue("describe");
							String paramType = el_row.attributeValue("type");
							String paramRequired = el_row.attributeValue("required");
							String paramValueType = el_row.attributeValue("vtype");
							Map<String, Object> paramFieldMap = new HashMap<String, Object>();
							paramFieldMap.put("describe", paramDescribe);
							paramFieldMap.put("type", paramType);
							paramFieldMap.put("required", paramRequired);
							paramFieldMap.put("vtype", paramValueType);
							paramsMap.put(paramName, paramFieldMap);
						}
						fieldMap.put("params", paramsMap);
					}
					argMap.put(name, fieldMap);
					argList.add(argMap);

				}
				map.put(op, argList);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) {
		// String a = "sdfdsf";
		// assert a != null;
		// checkRangeValue(23L, 11L, 22L, "age");
		initVaildatorCofing();
		Object xxx[] = new Object[3];
		UnitDTO u = new UnitDTO();
		Map<String, String> condtion = new HashMap<String, String>();
		condtion.put("name", "刘坤龙");
		condtion.put("age", "1000");
		u.setCode("11");
		u.setValue("22");
		List<Map<String, String>> list1=new ArrayList<Map<String, String>>();
		list1.add(condtion);
		List<String> list2=new ArrayList<String>();
		list2.add("1");
		list2.add("");
		xxx[0] = 1;
		xxx[1] = u;
		xxx[2] = condtion;
		xxx[3]=list1;
		xxx[4]=list2;
		
		vaildator("com.mes.product.web.UnitController.updateUnitTest", xxx);

	}
}
