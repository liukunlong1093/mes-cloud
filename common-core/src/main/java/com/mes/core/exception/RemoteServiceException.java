package com.mes.core.exception;

public class RemoteServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int code;
	private String msg;

	public RemoteServiceException(String msg) {
		super(msg);
	}

	public RemoteServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RemoteServiceException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
