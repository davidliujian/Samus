package com.sdu.samus.dao;

import com.sdu.samus.mapper.ArtMapper;
import com.sdu.samus.model.Art;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArtDao {
	@Autowired
	private ArtMapper artMapper;

	public int deUpdate(String artName){
		return artMapper.deUpdate(artName);
	}

	public int inUpdate(String artName){
		return artMapper.inUpdate(artName);
	}

	public int hasArt(String artName){
		return artMapper.hasArt(artName);
	}

	public int insertArt(Art art){
		return artMapper.insertSelective(art);
	}
}
