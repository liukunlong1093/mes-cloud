package com.mes.core.exception.enums;

import com.mes.core.exception.ExceptionEnums;

/**
  * 项目名称:	[common-core]
  * 包:	        [com.mes.core.exception]    
  * 类名称:		[ApiRuleExceptionEnums]  
  * 类描述:		[接口规则异常错误信息定义]
  * 创建人:		[liukl]   
  * 创建时间:	[2017年8月8日 下午4:55:01]   
  * 修改人:		[liukl]   
  * 修改时间:	[2017年8月8日 下午4:55:01]   
  * 修改备注:	[说明本次修改内容]  
  * 版本:		[v1.0]
 */
public enum ApiRuleExceptionEnums implements ExceptionEnums {
	/** dd*/
	MISSING_REQUIRED_ARGUMENTS(40, "缺少必填参数."), INVALID_ARGUMENTS(41, "非法参数.");

	/** 错误码 */
	public int code;
	
	/** 错误消息*/
	public String message;

	
	private ApiRuleExceptionEnums(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
