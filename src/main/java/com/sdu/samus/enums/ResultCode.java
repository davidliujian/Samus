package com.sdu.samus.enums;


public enum ResultCode {
	SUCCESS(0, "success"),
	UNKENOW_ERROR(-1, "unknow error"),
	BAD_REQUEST(400, "Bad Request"),
	NO_AUTHORIZATION(401, "NotAuthorization"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

	DB_EXCEPTION(999, "DB Exception"),
	RUNTIME_EXCEPTION(1000, "RuntimeException"),
	NULLPOINT_EXCEPTION(1001, "NullPointException"),
	CLASSCAST_EXCEPTION(1002, "ClassCastException"),
	IO_EXCEPTION(1003, "IOException"),
	UNKNOW_METHOD_EXCEPTION(1004, "Unknown method Exception"),
	INDEXOUTOFBOUNDS_EXCEPTION(1005, "IndexOutOfBoundsException"),
	NETWORK_EXCEPTION(1006, "NetWork Exception"),

	USERNAME_EMPTY(1007, "User name is empty"),
	PASSWORD_EMPTY(1008, "Password is empty"),
	USERNAME_PASSWORD_EMPTY(1009, "Account and password are empty"),
	PASSWORD_WRONG(1010, "Wrong password"),
	ACCOUNT_NO_REGISTERD(1011, "Account is not registered"),
	SCHOOL_EMPTY(1012,"School name is empty"),
	XUEHAO_EMPTY(1013,"Xuehao is empty"),
	XUEHAO_PASSWORD_EMPTY(1014,"Xuehao and password are empty"),
	REGISTER_WRONG(1015,"Register failed"),
	USERID_EMPTY(1016,"UserId empty"),
	ACCOUNT_HAS_REGISTERD(1017,"Account has registered"),
	DELETE_ERROR(1018, "删除错误"),
	UPDATE_ERROR(1019, "更改错误"),
	REFLECT_ERROR(1020, "反射错误"),
	NICKNAME_PASSWORD_EMPTY(1021,"昵称和密码为空！"),
	PUBLISH_WRONG(1022,"Publish failed"),
	END_RECORD(1023,"NO more records"),
	RECORD_EMPTY(1024,"Record is empty"),
	LOG_INSERT_ERROR(1025,"Insert log failed"),
	NOT_LOGIN(1026,"Have not login");

	private Integer code;

	private String msg;

	ResultCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public ResultCode setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}
