package com.sdu.samus.vo;

public class UserRegisterVO {
	private String xuehao;
	private String schoolName;
	private byte gender;
	private String password;

	public UserRegisterVO(){

	}

	public UserRegisterVO(String xuehao, String schoolName,byte gender, String password) {
		this.xuehao = xuehao;
		this.schoolName = schoolName;
		this.gender = gender;
		this.password = password;
	}

	public String getXuehao() {
		return xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public byte getGender(){
		return gender;
	}

	public void setGender(byte gender){
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserRegisterVO{" +
				"xuehao='" + xuehao + '\'' +
				", schoolName='" + schoolName + '\'' +
				", password='" + password + '\'' +
				'}';
	}

}
