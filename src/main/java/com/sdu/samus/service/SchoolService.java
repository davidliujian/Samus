package com.sdu.samus.service;

import com.sdu.samus.dao.SchoolDao;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.model.School;
import com.sdu.samus.util.StringUtil;
import com.sdu.samus.vo.UserRegisterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
	@Autowired
	private SchoolDao schoolDao;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public School getSchoolByName(UserRegisterVO user)throws ParameterException {
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(user.getSchoolName())){
			logger.info("SchoolService --- [user.getSchoolName]     :"+user.getSchoolName());
			pe.addError(ResultCode.SCHOOL_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("SchoolService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}
		return schoolDao.findSchoolByName(user.getSchoolName());
	}

	public School getSchoolById5(String schoolid5)throws ParameterException {
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(schoolid5)){
			logger.info("SchoolService --- [user.getSchoolid5]     :"+schoolid5);
			pe.addError(ResultCode.SCHOOL_EMPTY);
		}
		if(pe.hasErrors()){
			logger.info("SchoolService --- [ParameterException.hasErrors]     :"+pe.hasErrors());
			throw pe;
		}
		return schoolDao.findSchoolById5(schoolid5);
	}
}
