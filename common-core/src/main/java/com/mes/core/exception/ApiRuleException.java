package com.mes.core.exception;

/**
  * 项目名称:	[common-core]
  * 包:	        [com.mes.core.exception]    
  * 类名称:		[ApiRuleException]  
  * 类描述:		[接口规则异常]
  * 创建人:		[liukl]   
  * 创建时间:	[2017年8月8日 下午4:57:43]   
  * 修改人:		[liukl]   
  * 修改时间:	[2017年8月8日 下午4:57:43]   
  * 修改备注:	[说明本次修改内容]  
  * 版本:		[v1.0]
 */
public class ApiRuleException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private ExceptionEnums exceptionEnums;

	public ApiRuleException(ExceptionEnums exceptionEnums) {
		super(exceptionEnums.getMessage());
		this.exceptionEnums = exceptionEnums;
	}

	public ApiRuleException(ExceptionEnums exceptionEnums, String s) {
		super(s);
		this.exceptionEnums = exceptionEnums;
	}

	public ExceptionEnums getExceptionEnums() {
		return exceptionEnums;
	}
}
