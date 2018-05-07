package com.sdu.samus.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordDTO {
	private String userid;
	private Integer recordid;
	private String content;
	private ArrayList<String> picture;
	private String createtime;

	public RecordDTO(){}

	public RecordDTO(String userid, Integer recordid, String content, ArrayList<String> picture, String createtime) {
		this.userid = userid;
		this.recordid = recordid;
		this.content = content;
		this.picture = picture;
		this.createtime = createtime;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getRecordid() {
		return recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<String> getPicture() {
		return picture;
	}

	public void setPicture(ArrayList<String> picture) {
		this.picture = picture;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		SimpleDateFormat time=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		this.createtime = time.format(createtime);
	}

}
