package com.sdu.samus.service;

import com.sdu.samus.dao.UserDao;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.UserInfo;
import com.sdu.samus.util.StringUtil;
import com.sdu.samus.vo.UserLoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserInfo getUserInfo(String id) throws ParameterException ,ServiceException{
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty((id))){
			pe.addError(ResultCode.USERID_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}
		UserInfo user =  userDao.findUserInfoById(id);
		if(user == null){
			throw new ServiceException(ResultCode.ACCOUNT_NO_REGISTERD);
		}
		return user;
	}

	/**
	 * 登录
	 * @param user
	 * @return
	 * @throws ParameterException
	 */
	public UserInfo login(UserLoginVO user) throws ParameterException {
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(user.getUserId())){
			logger.info("UserService --- [user.getUserId]     :"+user.getUserId());
			pe.addError(ResultCode.USERNAME_EMPTY);
		}
		if(StringUtil.isEmpty(user.getPassword())){
			logger.info("UserService --- [user.getPassword]     :"+user.getPassword());
			pe.addError(ResultCode.PASSWORD_EMPTY);
		}
		if(pe.getErrors().size() == 2){
			pe.clearErrors();
			pe.addError(ResultCode.USERNAME_PASSWORD_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}

		UserInfo userinfo = userDao.findUserInfoById(user.getUserId());

		//判断用户是否注册
		if(userinfo == null){
			throw new ServiceException(ResultCode.ACCOUNT_NO_REGISTERD);
		}
		//判断密码是否正确
		if(!user.getPassword().equals(userinfo.getPassword())){
			throw new ServiceException(ResultCode.PASSWORD_WRONG);
		}

		return userinfo;
	}

	/**
	 * 注册
	 * @param schoolid5
	 * @param xuehao
	 * @param password
	 * @return
	 * @throws ParameterException
	 */
	public int register(String schoolid5,String xuehao,byte gender,String password) throws ParameterException,ServiceException{
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(xuehao)){
			logger.info("UserService --- [xuehao]     :"+xuehao);
			pe.addError(ResultCode.XUEHAO_EMPTY);
		}
		if(StringUtil.isEmpty(password)){
			logger.info("UserService --- [password]     :"+password);
			pe.addError(ResultCode.PASSWORD_EMPTY);
		}
		if(pe.getErrors().size() == 2){
			pe.clearErrors();
			pe.addError(ResultCode.XUEHAO_PASSWORD_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("UserService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}

		//新建UserInfo对象
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(schoolid5+xuehao);
		userInfo.setPassword(password);
		userInfo.setGender(gender);
		userInfo.setSchoolid5(schoolid5);

		int result = userDao.registerUser(userInfo);

		if(result == 0){
			throw new ServiceException(ResultCode.REGISTER_WRONG);
		}
		return  result;
	}

}
