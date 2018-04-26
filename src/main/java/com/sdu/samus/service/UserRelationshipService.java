package com.sdu.samus.service;

import com.sdu.samus.dao.UserRelationshipDao;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.UserRelationship;
import com.sdu.samus.model.UserRelationshipWithBLOBs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserRelationshipService {
	@Autowired
	private UserRelationshipDao userRelationshipDao;

	private static final Logger logger = LoggerFactory.getLogger(UserRelationshipService.class);

	/**
	 * 根据userId获取UserRelationshipWithBLOBs
	 * @param userId
	 * @return
	 * @throws ParameterException
	 * @throws ServiceException
	 * @throws DataAccessException
	 */
	public UserRelationshipWithBLOBs getUserRelationship(String userId) throws ServiceException,DataAccessException{
		return userRelationshipDao.findUserRelationship(userId);
	}

	public int setActive(String userId) throws ServiceException{
		//新建userRelationship对象
		UserRelationshipWithBLOBs userRelationship = new UserRelationshipWithBLOBs();
		userRelationship.setUserid(userId);
		userRelationship.setActive("1");
		int res = userRelationshipDao.setActive(userRelationship);
		logger.info("修改Active为1成功！！");
		if(res == 0){
			throw new ServiceException(ResultCode.DB_EXCEPTION);
		}
		return res;
	}

	public int registerRelationship(String schoolid5,String xuehao,String city){
		//新建userRelationship对象
		UserRelationshipWithBLOBs userRelationship = new UserRelationshipWithBLOBs();
		userRelationship.setUserid(schoolid5+xuehao);
		userRelationship.setCity(city);

		return userRelationshipDao.registerRelationship(userRelationship);
	}
}
