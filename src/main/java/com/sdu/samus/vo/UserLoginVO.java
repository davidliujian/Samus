package com.sdu.samus.vo;

public class UserLoginVO {
	private String userId;
	private String password;

	public UserLoginVO(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}

	public UserLoginVO() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserVo [userId=" + userId + ", password=" + password + "]";
	}
}
