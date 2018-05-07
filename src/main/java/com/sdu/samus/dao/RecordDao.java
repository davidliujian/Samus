package com.sdu.samus.dao;

import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.mapper.RecordMapper;
import com.sdu.samus.model.Pagination;
import com.sdu.samus.model.Record;
import com.sdu.samus.model.RecordKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecordDao {

	@Autowired
	private RecordMapper recordMapper;

	public int insertRecord(Record record){
		return recordMapper.insertSelective(record);
	}

	public List<Record> getRecords(Pagination pagination,String userid) throws ParameterException,DataAccessException{
		pagination.setTotalCount(this.getRecordCount(userid));
		if (pagination.getCurrentPage() > pagination.getPageCount()) {
//			pagination.setCurrentPage(pagination.getPageCount());
			ParameterException pe = new ParameterException();
			pe.addError(ResultCode.END_RECORD);
			throw pe;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", pagination.getOffset());
		params.put("pageSize", pagination.getPageSize());
		params.put("userid",userid);

		return recordMapper.selectRecordList(params);

	}

	public int getRecordCount(String userid) {
		return recordMapper.getRecordCount(userid);
	}

	public Record getRecord(RecordKey key) throws DataAccessException{
		return recordMapper.selectByPrimaryKey(key);
	}

	public int deleteRecord(RecordKey key)throws DataAccessException{
		return recordMapper.deleteByPrimaryKey(key);
	}
}
