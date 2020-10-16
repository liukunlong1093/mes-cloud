package com.mes.core.pojos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务响应对象
 * @author Administrator
 * @date 2018/4/28
 */
@JsonInclude(Include.NON_NULL)
@ApiModel
public class ServiceResponse<T> implements Serializable {

	private static final long serialVersionUID = 4032538292214320228L;

	/** 200-成功 */
	private final static String CODE_SUCCESS = "200";

	/**
	 * 成功消息
	 */
	private static final String CODE_SUCCESS_MSG = "success";

	/** 500-业务逻辑错误*/
	public final static String CODE_ERROR_SERVICE = "500";

	/** 返回码*/
	@ApiModelProperty(value = "返回码")
	private String code;

	/** 返回短消息*/
	@ApiModelProperty(value = "返回短消息")
	private String msg;

	/** 总条数当且仅当分页时需要*/
	@ApiModelProperty(value = "总条数当且仅当分页时需要")
	private Long total;

	/** 返回数据*/
	@ApiModelProperty(value = "返回数据")
	private T data;

	/** 是否执行成功*/
	@ApiModelProperty(value = "是否执行成功")
	private boolean success;

	public ServiceResponse() {
	}

	/**
	 * 用于构造简单服务响应对象
	 * @param code 返回码
	 * @param msg 返回短消息
	 */
	private ServiceResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private ServiceResponse(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	private ServiceResponse(String code, String msg, T data, Long total) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.total = total;
	}

	/**
	 * 操作成功
	 * @param data 数据对象
	 * @return 服务响应对象
	 */
	public static <T> ServiceResponse<T> handleSuccess(T data) {
		return new ServiceResponse<>(CODE_SUCCESS,CODE_SUCCESS_MSG, data);
	}

	/**
	 * 操作成功
	 * @param data 数据对象
	 * @return 服务响应对象
	 */
	public static <T> ServiceResponse<T> handleSuccess(T data, Long total) {
		return new ServiceResponse<>(CODE_SUCCESS,CODE_SUCCESS_MSG, data, total);
	}

	/**
	 * 操作成功
	 * @return 服务响应对象
	 */
	public static <T> ServiceResponse<T> handleSuccess() {
		return new ServiceResponse<>(CODE_SUCCESS,CODE_SUCCESS_MSG);
	}

	/**
	 * 操作失败
	 * @param code 错误返回码
	 * @param msg 错误短消息
	 * @return 服务响应对象
	 */
	public static <T> ServiceResponse<T> handleFail(String code, String msg) {
		return new ServiceResponse<>(code, msg);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	public boolean isSuccess() {
		if (CODE_SUCCESS.equals(code)) {
			success=true;
		}
		return success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
