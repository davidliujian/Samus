package com.sdu.samus.dao;

import com.sdu.samus.mapper.SportMapper;
import com.sdu.samus.model.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SportDao {
	@Autowired
	private SportMapper sportMapper;
	public int deUpdate(String sportName){
		return sportMapper.deUpdate(sportName);
	}

	public int inUpdate(String sportName){
		return sportMapper.inUpdate(sportName);
	}

	public int hasSport(String sportName){
		return sportMapper.hasSport(sportName);
	}

	public int insertSport(Sport sport){
		return sportMapper.insertSelective(sport);
	}
}
