package com.sdu.samus.vo;

import java.util.List;

public class RecordVO {
	private String content;
	private List<String> picture;

	public RecordVO(){}

	public RecordVO(String content,List<String> picture){
		this.content = content;
		this.picture = picture;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getPicture() {
		return picture;
	}

	public void setPicture(List<String> picture) {
		this.picture = picture;
	}



}
