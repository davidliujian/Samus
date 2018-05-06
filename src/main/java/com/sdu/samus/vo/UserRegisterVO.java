package com.sdu.samus.vo;

public class UserRegisterVO {
	private String xuehao;
	private String schoolId5;
	private byte gender;
	private String password;

	public UserRegisterVO(){

	}

	public UserRegisterVO(String xuehao, String schoolId5,byte gender, String password) {
		this.xuehao = xuehao;
		this.schoolId5 = schoolId5;
		this.gender = gender;
		this.password = password;
	}

	public String getXuehao() {
		return xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}

	public String getSchoolId5() {
		return schoolId5;
	}

	public void setSchoolId5(String schoolName) {
		this.schoolId5 = schoolName;
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
				", schoolName='" + schoolId5 + '\'' +
				", password='" + password + '\'' +
				'}';
	}

}
