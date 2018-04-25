package com.sdu.samus.vo;

public class ResultVO {
	private final int code;
	private final String msg;
	private final Object data;

	public static class Builder {
		private int code;
		private String msg;
		private Object data;

		public Builder() {

		}

		public Builder code(int code) {
			this.code = code;
			return this;
		}

		public Builder msg(String msg) {
			this.msg = msg;
			return this;
		}

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public ResultVO build() {
			return new ResultVO(this);
		}
	}

	private ResultVO(Builder builder) {
		this.code = builder.code;
		this.msg = builder.msg;
		this.data = builder.data;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}

}
