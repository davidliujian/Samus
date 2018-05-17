package com.sdu.samus.vo;

import com.sdu.samus.model.UserInfo;
import sun.misc.BASE64Encoder;

public class UserGetVO {

		private String userid;
		private String nickname;
		private String schoolName;
		private Short age;
		private Byte gender;
		private String intro;
		private String phone;
		private String art;
		private String cartoon;
		private String food;
		private String idol;
		private String game;
		private String movie;
		private String music;
		private String reading;
		private String sport;
		private String travel;
		private String avatar;

	public UserGetVO() {}

	public UserGetVO(String userid, String nickname, String schoolName, Short age, Byte gender, String intro,
					 String phone, String art, String cartoon, String food, String idol, String game, String movie,
					 String music, String reading, String sport, String travel, String avatar) {
		this.userid = userid;
		this.nickname = nickname;
		this.schoolName = schoolName;
		this.age = age;
		this.gender = gender;
		this.intro = intro;
		this.phone = phone;
		this.art = art;
		this.cartoon = cartoon;
		this.food = food;
		this.idol = idol;
		this.game = game;
		this.movie = movie;
		this.music = music;
		this.reading = reading;
		this.sport = sport;
		this.travel = travel;
		this.avatar = avatar;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
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

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getCartoon() {
		return cartoon;
	}

	public void setCartoon(String cartoon) {
		this.cartoon = cartoon;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getIdol() {
		return idol;
	}

	public void setIdol(String idol) {
		this.idol = idol;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getTravel() {
		return travel;
	}

	public void setTravel(String travel) {
		this.travel = travel;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public static UserGetVO UserInfoToUserGetVO(UserInfo userInfo){
		byte[] avatar = userInfo.getAvatar();
		String base64="";
		if(avatar == null){
		}else{
			BASE64Encoder encoder = new BASE64Encoder();
			base64 = encoder.encode(avatar).trim();//转换成base64串
			base64 = base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
		}

		UserGetVO user = new UserGetVO(userInfo.getUserid(),userInfo.getNickname(),userInfo.getSchoolid5(), userInfo.getAge(),
				userInfo.getGender(),userInfo.getIntro(), userInfo.getPhone(), userInfo.getArt(), userInfo.getCartoon(),
				userInfo.getFood(), userInfo.getIdol(), userInfo.getGame(), userInfo.getMovie(),
				userInfo.getMusic(), userInfo.getReading(), userInfo.getSport(), userInfo.getTravel(), base64);

		return user;
	}
}
