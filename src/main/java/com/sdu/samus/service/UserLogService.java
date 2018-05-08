package com.sdu.samus.service;

import com.sdu.samus.dao.UserLogDao;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogService {
	@Autowired
	private UserLogDao userLogDao;

	public int insertLog(UserLog userLog)throws  ServiceException{
		int res = userLogDao.insertLog(userLog);
		if(res == 0){
			throw new ServiceException(ResultCode.LOG_INSERT_ERROR);
		}
		return res;
	}

	public int updateLog(UserLog userLog){
		int res = userLogDao.updateLog(userLog);

		return res;
	}
}
