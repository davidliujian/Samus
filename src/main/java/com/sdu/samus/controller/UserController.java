package com.sdu.samus.controller;

import com.sdu.samus.Constants;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.School;
import com.sdu.samus.model.UserInfo;
import com.sdu.samus.service.SchoolService;
import com.sdu.samus.service.UserService;
import com.sdu.samus.util.ResultVoGenerator;
import com.sdu.samus.util.SessionUtil;
import com.sdu.samus.vo.ResultVO;
import com.sdu.samus.vo.UserLoginVO;
import com.sdu.samus.vo.UserRegisterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SchoolService schoolService;

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value="/getUserInfo",method=RequestMethod.GET)
	public UserInfo getUserInfo(@RequestParam("id") String id) throws ParameterException {
		return userService.getUserInfo(id);
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResultVO login(@RequestBody UserLoginVO user) throws ParameterException,ServiceException {
		//获取信息
		UserInfo userInfo = userService.login(user);
		//保存用户session
		userInfo.setPassword(null);
		SessionUtil.addSession(Constants.USER, userInfo);
		return ResultVoGenerator.success();

	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResultVO register(@RequestBody UserRegisterVO user) throws ParameterException,ServiceException,DataAccessException{
		logger.info(user.getSchoolName()+"  "+user.getXuehao()+"    "+user.getGender()+"    "+user.getPassword());
		//根据学校名获取学校
		School school = schoolService.getSchoolByName(user);
		String schoolid5 = school.getSchoolid5();

		//检测该账户是否已经注册
		try{
			UserInfo userInfo = this.getUserInfo(schoolid5+user.getXuehao());
		}catch(ServiceException se){
			if(se.getCode() == 1011){		//如果没有注册
				int res = userService.register(schoolid5,user.getXuehao(),user.getGender(),user.getPassword());

				//保存用户session
				UserInfo userInfo = this.getUserInfo(schoolid5+user.getXuehao());
				userInfo.setPassword(null);
				SessionUtil.addSession(Constants.USER, userInfo);

				return ResultVoGenerator.success();
			}
		}catch(DataAccessException de){
			throw de;
		}
		//如果已经账户已经注册
		return ResultVoGenerator.error(ResultCode.ACCOUNT_HAS_REGISTERD);


	}
}
