package com.sdu.samus.dao;

import com.sdu.samus.mapper.MusicMapper;
import com.sdu.samus.mapper.ReadingMapper;
import com.sdu.samus.model.Music;
import com.sdu.samus.model.Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReadingDao {
	@Autowired
	private ReadingMapper readingMapper;
	public int deUpdate(String readingName){
		return readingMapper.deUpdate(readingName);
	}

	public int inUpdate(String readingName){
		return readingMapper.inUpdate(readingName);
	}

	public int hasReading(String readingName){
		return readingMapper.hasReading(readingName);
	}

	public int insertReading(Reading reading){
		return readingMapper.insertSelective(reading);
	}
}
