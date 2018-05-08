package com.sdu.samus.dao;

import com.sdu.samus.mapper.ReadingMapper;
import com.sdu.samus.mapper.TravelMapper;
import com.sdu.samus.model.Reading;
import com.sdu.samus.model.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TravelDao {
	@Autowired
	private TravelMapper travelMapper;
	public int deUpdate(String travelName){
		return travelMapper.deUpdate(travelName);
	}

	public int inUpdate(String travelName){
		return travelMapper.inUpdate(travelName);
	}

	public int hasTravel(String travelName){
		return travelMapper.hasTravel(travelName);
	}

	public int insertTravel(Travel travel){
		return travelMapper.insertSelective(travel);
	}
}
