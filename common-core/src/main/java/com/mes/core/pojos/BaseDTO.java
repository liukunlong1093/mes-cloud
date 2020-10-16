package com.mes.core.pojos;
/**
 * 基本DTO对象用于封装每个DTO必要属性
 * @author Administrator
 * @date 2018/4/28
 */
public class BaseDTO extends PrintableAndSerializableDTO {
	
	/**
	 * 是否发送事物消息
	 */
	public boolean retry=true;

	/**
	 * 是否发送事物消息
	 * @return
	 */
	public boolean isRetry() {
		return retry;
	}

	/**
	 * 设置是否发送事物消息
	 * @param retry 是否发送事物消息
	 */
	public void setRetry(boolean retry) {
		this.retry = retry;
	}
}
