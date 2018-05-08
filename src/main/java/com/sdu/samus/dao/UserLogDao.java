package com.sdu.samus.dao;

import com.sdu.samus.mapper.UserLogMapper;
import com.sdu.samus.model.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class UserLogDao {
	@Autowired
	private UserLogMapper userLogMapper;

	public int insertLog(UserLog userLog)throws DataAccessException {
		return userLogMapper.insertSelective(userLog);
	}

	public int updateLog(UserLog userLog)throws DataAccessException {
		return userLogMapper.updateByPrimaryKeySelective(userLog);
	}
}
