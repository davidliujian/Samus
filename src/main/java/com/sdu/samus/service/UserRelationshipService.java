package com.sdu.samus.service;

import com.sdu.samus.dao.UserRelationshipDao;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.UserRelationship;
import com.sdu.samus.model.UserRelationshipWithBLOBs;
import com.sdu.samus.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

	/**
	 * 左滑不喜欢，更新数据库里的totalCount
	 * @param userId
	 * @return
	 * @throws ParameterException
	 * @throws ServiceException
	 */
	public int updateDislike(String userId) throws ParameterException,ServiceException{
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty((userId))){
			pe.addError(ResultCode.USERID_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}

		int res = userRelationshipDao.updateDislike(userId);
		logger.info("修改totalCount+1成功！！");
		if(res == 0){
			logger.info("左滑不喜欢更改数据库失败！");
			throw new ServiceException(ResultCode.DB_EXCEPTION);
		}
		return res;
	}

	/**
	 * 右滑喜欢，更新数据库里的likeCount,totalCount,hobbyCount
	 * @param userId
	 * @return
	 * @throws ParameterException
	 * @throws ServiceException
	 */
	public int updateLike(String userId ,String contactId) throws ParameterException,ServiceException{
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty((contactId))){
			pe.addError(ResultCode.USERID_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}

		//获取喜欢人的featurevector
		UserRelationshipWithBLOBs userRelationshipWithBLOBs1 = userRelationshipDao.findUserRelationship(contactId);
		String featureVector = userRelationshipWithBLOBs1.getFeaturevector();
		logger.info("featureVector:"+featureVector);
		//获取当前用户的hobbyCount
		UserRelationshipWithBLOBs userRelationshipWithBLOBs = userRelationshipDao.findUserRelationship(userId);
		String hobbyCount = userRelationshipWithBLOBs.getHobbycount();
		ArrayList<String> array = StringUtil.split(hobbyCount,";");

		logger.info("分割hobbyCount成功！！！");

		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < array.size(); i++){
			int a = Integer.parseInt(array.get(i));
			int b = Integer.parseInt(featureVector.charAt(i)+"");
			int newx = a + b ;
			sb.append(newx).append(";");
		}
		sb.deleteCharAt(sb.lastIndexOf(";"));
		String newHobbyCount = sb.toString();

		logger.info("构造新的hobbyCount成功！！！"+newHobbyCount );

		int res = userRelationshipDao.updateLike(userId,newHobbyCount);
		if(res == 0){
			logger.info("右滑喜欢更改数据库失败！");
			throw new ServiceException(ResultCode.DB_EXCEPTION);
		}
		return res;
	}
}
