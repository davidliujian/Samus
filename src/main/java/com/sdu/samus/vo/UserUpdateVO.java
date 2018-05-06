package com.sdu.samus.vo;

public class UserUpdateVO {
	private String password;
	private String nickname;
	private byte[] avatar;
	private Short age;
	private byte gender;
	private String intro;
	private String phone;

	public UserUpdateVO(){}

	public UserUpdateVO(String password, String nickname, byte[] avatar, Short age, byte gender, String intro, String phone) {
		this.password = password;
		this.nickname = nickname;
		this.avatar = avatar;
		this.age = age;
		this.gender = gender;
		this.intro = intro;
		this.phone = phone;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
