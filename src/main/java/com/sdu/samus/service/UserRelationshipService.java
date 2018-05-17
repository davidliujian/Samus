package com.sdu.samus.service;

import com.sdu.samus.dao.UserRelationshipDao;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.UserInfo;
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
	@Autowired
	private UserService userService;
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

	public int registerRelationship(String schoolid5,String xuehao,String city,String hobbyCount){
		//新建userRelationship对象
		UserRelationshipWithBLOBs userRelationship = new UserRelationshipWithBLOBs();
		userRelationship.setUserid(schoolid5+xuehao);
		userRelationship.setCity(city);
		userRelationship.setHobbycount(hobbyCount);
		userRelationship.setGrouplist("好友");

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

		UserInfo user = userService.getUserInfo(userId);
		UserInfo likeuser = userService.getUserInfo(contactId);

		//获取喜欢人的featurevector,likeList
		UserRelationshipWithBLOBs userRelationshipWithBLOBs1 = userRelationshipDao.findUserRelationship(contactId);
		String featureVector = userRelationshipWithBLOBs1.getFeaturevector();
		String likeList1 = userRelationshipWithBLOBs1.getLikelist();
		String contact1 = userRelationshipWithBLOBs1.getContactlist();
		logger.info("featureVector:"+featureVector);
		logger.info("likeList1:"+likeList1);
		ArrayList<String> likeArray1 = StringUtil.split(likeList1,";");

		//获取当前用户的hobbyCount,likeList
		UserRelationshipWithBLOBs userRelationshipWithBLOBs = userRelationshipDao.findUserRelationship(userId);
		String hobbyCount = userRelationshipWithBLOBs.getHobbycount();
		String likeList = userRelationshipWithBLOBs.getLikelist();
		String contact = userRelationshipWithBLOBs.getContactlist();
		ArrayList<String> array = StringUtil.split(hobbyCount,";");

		String like="";
		if(!StringUtil.isEmpty(likeList)){
			like = likeList+";"+contactId;
		}else{
			like = contactId;
		}

		logger.info("分割hobbyCount成功！！！");

		//如果喜欢人也喜欢自己,更新双方的likeList和contactList
		if(likeArray1.contains(userId)){
			//删除喜欢人的likeList列表里的自己
			likeArray1.remove(userId);
			String like1 = "";
			if(likeArray1.size()!=0){
				StringBuffer sblike= new StringBuffer();
				for(String s : likeArray1){
					sblike.append(s).append(";");
				}
				sblike.deleteCharAt(sblike.lastIndexOf(";"));
				like1 = sblike.toString();
			}

			String newContact1="";
			if(StringUtil.isEmpty(contact1)){
				newContact1 = userId+":"+user.getNickname()+":好友";
			}else{
				newContact1 = contact1+"\\10"+userId+":"+user.getNickname()+":好友";
			}


			//更新喜欢人的likeList和contactList
			UserRelationshipWithBLOBs user1 = new UserRelationshipWithBLOBs();
			user1.setUserid(contactId);
			user1.setLikelist(like1);
			user1.setContactlist(newContact1);
			userRelationshipDao.update(user1);

			//更新自己的contactList
			UserRelationshipWithBLOBs user2 = new UserRelationshipWithBLOBs();
			user2.setUserid(userId);
			String newContact="";
			if(StringUtil.isEmpty(contact)){
				newContact = contactId+":"+likeuser.getNickname()+":好友";
			}else{
				newContact = contact+"\\10"+contactId+":"+likeuser.getNickname()+":好友";
			}

			user2.setContactlist(newContact);
			userRelationshipDao.update(user2);
			like = likeList;
		}

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

		int res = userRelationshipDao.updateLike(userId,newHobbyCount,like);

		if(res == 0){
			logger.info("右滑喜欢更改数据库失败！");
			throw new ServiceException(ResultCode.DB_EXCEPTION);
		}
		return res;
	}
}
