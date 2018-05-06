package com.sdu.samus.dao;

import com.sdu.samus.mapper.RecordMapper;
import com.sdu.samus.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecordDao {

	@Autowired
	private RecordMapper recordMapper;

	public int insertRecord(Record record){
		return recordMapper.insertSelective(record);
	}
}
