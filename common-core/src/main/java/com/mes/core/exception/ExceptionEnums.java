package com.mes.core.exception;
/**
  * 项目名称:	[common-core]
  * 包:	        [com.mes.core.exception]    
  * 类名称:		[ExceptionEnums]  
  * 类描述:		[统一获取异常接口]
  * 创建人:		[liukl]   
  * 创建时间:	[2017年8月8日 下午4:46:44]   
  * 修改人:		[liukl]   
  * 修改时间:	[2017年8月8日 下午4:46:44]   
  * 修改备注:	[说明本次修改内容]  
  * 版本:		[v1.0]
 */
public interface ExceptionEnums {
	/**
	 * 获取异常错误码
	 * @return 错误码
	 */
	public int getCode();
	
	/**
	 * 获取异常错误信息
	 * @return 错误信息
	 */
	public String getMessage();
}
