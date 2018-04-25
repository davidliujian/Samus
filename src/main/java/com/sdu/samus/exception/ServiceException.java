package com.sdu.samus.exception;

import com.sdu.samus.enums.ResultCode;

public class ServiceException extends RuntimeException {

	private int code;
	private String message;
	private ResultCode resultCode;

	public ServiceException(ResultCode resultCode) {
		super(resultCode.getMsg());
		this.resultCode = resultCode;
		this.code = resultCode.getCode();
		this.message = resultCode.getMsg();
	}

	public ServiceException(String message) {
		super();
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public ResultCode getResultCode() {
		return resultCode;
	}
}
