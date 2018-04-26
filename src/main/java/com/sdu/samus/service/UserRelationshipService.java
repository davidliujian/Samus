package com.sdu.samus.service;

import com.sdu.samus.dao.UserRelationshipDao;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.UserRelationship;
import com.sdu.samus.model.UserRelationshipWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserRelationshipService {
	@Autowired
	private UserRelationshipDao userRelationshipDao;

	public UserRelationshipWithBLOBs getUserRelationship(String userId) throws ParameterException,ServiceException,DataAccessException{
		return userRelationshipDao.findUserRelationship(userId);
	}

	public int registerRelationship(String schoolid5,String xuehao,String city){
		//新建userRelationship对象
		UserRelationshipWithBLOBs userRelationship = new UserRelationshipWithBLOBs();
		userRelationship.setUserid(schoolid5+xuehao);
		userRelationship.setCity(city);

		return userRelationshipDao.registerRelationship(userRelationship);
	}
}
