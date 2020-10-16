package com.mes.core.exception.enums;

import com.mes.core.exception.ExceptionEnums;

/**
  * 项目名称:	[common-core]
  * 包:	        [com.mes.core.exception]    
  * 类名称:		[ApiRuleExceptionEnums]  
  * 类描述:		[系统异常错误信息定义]
  * 创建人:		[liukl]   
  * 创建时间:	[2017年8月8日 下午4:55:01]   
  * 修改人:		[liukl]   
  * 修改时间:	[2017年8月8日 下午4:55:01]   
  * 修改备注:	[说明本次修改内容]  
  * 版本:		[v1.0]
 */
public enum SystemExceptionEnums implements ExceptionEnums {

	/**
	 * 1-100      系统级别异常
	 */
	UPLOAD_FAIL(1, "图片上传失败."), 
	SESSION_CALL_LIMITED(2, "会话调用次数超限."), 
	APP_CALL_LIMITED(3, "应用调用次数超限."), 
	APP_CALL_EXCEEDS_LIMITEDFREQUENCY(4, "应用调用频率超限."), 
	SERVICE_CURRENTLY_UNAVAILABLE(5, "服务不可用."), 
	INSUFFICIENT_USER_PERMISSIONS(6, "用户权限不足."), 
	REMOTE_SERVICE_ERROR(7, "远程服务出错."), 
	MISSING_METHOD(8, "缺少方法名参数."),
	NVALID_METHOD(9, "不存在的方法名."), 
	INVALID_FORMAT(10, "非法数据格式."), 
	MISSING_SIGNATURE(11, "缺少签名参数."), 
	INVALID_SIGNATURE(12, "非法签名."), 
	MISSING_TIMESTAMP(13, "缺少时间戳参数."), 
	INVALID_TIMESTAMP(14, "非法的时间戳参数."), 
	MISSING_VERSION(15, "缺少版本参数."), 
	INVALID_VERSION(16, "非法的版本参数."), 
	UNSUPPORTED_VERSION(17, "不支持的版本号."), 
	MISSING_REQUIRED_ARGUMENTS(18, "缺少必选参数."), 
	INVALID_ARGUMENTS(19, "非法的参数."), 
	FORBIDDEN_REQUEST(20, "请求被禁止."), 
	PARAMETER_ERROR(21, "参数错误."),
	TOKEN_EXPIRED(22, "token已失效请重新登录."),
	MISSING_SECRET(23, "缺少秘钥参数."), 
	INVALID_SECRET(24, "非法的秘钥参数."),
	URL_EXPIRED(25, "URL已失效."),
	SIGNATURE_FAIL(26,"签名失败")
	;
	
	/** 错误码 */
	public int code;

	/** 错误消息*/
	public String message;

	private SystemExceptionEnums(int code, String message) {
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
