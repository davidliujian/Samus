package com.sdu.samus.dao;

import com.sdu.samus.mapper.MusicMapper;
import com.sdu.samus.model.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MusicDao {
	@Autowired
	private MusicMapper musicMapper;
	public int deUpdate(String musicName){
		return musicMapper.deUpdate(musicName);
	}

	public int inUpdate(String musicName){
		return musicMapper.inUpdate(musicName);
	}

	public int hasMusic(String musicName){
		return musicMapper.hasMusic(musicName);
	}

	public int insertMusic(Music music){
		return musicMapper.insertSelective(music);
	}
}
