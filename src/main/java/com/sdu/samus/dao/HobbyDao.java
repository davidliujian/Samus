package com.sdu.samus.dao;

import com.sdu.samus.mapper.ArtMapper;
import com.sdu.samus.mapper.HobbyInfoMapper;
import com.sdu.samus.model.Art;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HobbyDao {

	@Autowired
	private HobbyInfoMapper hobbyInfoMapper;

	//更新hobbyinfo
	public int hobbyUpdate(String hobby){
		return hobbyInfoMapper.updateCount(hobby);
	}

}
