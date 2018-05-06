package com.sdu.samus.controller;

import com.sdu.samus.Constants;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.School;
import com.sdu.samus.model.UserInfo;
import com.sdu.samus.model.UserRelationshipWithBLOBs;
import com.sdu.samus.service.SchoolService;
import com.sdu.samus.service.UserRelationshipService;
import com.sdu.samus.service.UserService;
import com.sdu.samus.util.ResultVoGenerator;
import com.sdu.samus.util.SessionUtil;
import com.sdu.samus.util.StringUtil;
import com.sdu.samus.vo.ResultVO;
import com.sdu.samus.vo.UserLoginVO;
import com.sdu.samus.vo.UserRegisterVO;
import com.sdu.samus.vo.UserUpdateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private UserRelationshipService userRelationshipService;

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public UserInfo getUserInfo(@RequestParam("id") String id) throws ParameterException {
		UserInfo userInfo = userService.getUserInfo(id);
//		userInfo.setPassword(null);
		return userInfo;
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResultVO login(@RequestBody UserLoginVO user) throws ParameterException,ServiceException {
		logger.info("------------------登录-------------------------");
		//获取信息
		UserInfo userInfo = userService.login(user);
		//保存用户session
		userInfo.setPassword(null);
		SessionUtil.addSession(Constants.USER, userInfo);

		return ResultVoGenerator.success();

	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResultVO register(@RequestBody UserRegisterVO user) throws ParameterException,ServiceException,DataAccessException{
		logger.info("------------------注册--------------------------");
		logger.info(user.getSchoolId5()+"  "+user.getXuehao()+"    "+user.getGender()+"    "+user.getPassword());
		//根据学校名获取学校
		School school = schoolService.getSchoolBySchoolId5(user);

		//开始决定前端传过来的是学校名，之后改成前端存储schoolId5,直接传schoolId5
		String schoolid5 = user.getSchoolId5();

		//检测该账户是否已经注册
		try{
			UserInfo userInfo = this.getUserInfo(schoolid5+user.getXuehao());
		}catch(ServiceException se){
			if(se.getCode() == 1011){		//如果没有注册
				int res = userService.register(schoolid5,user.getXuehao(),user.getGender(),user.getPassword());
				logger.info("register    [res]:"+res);
				int res1 = userRelationshipService.registerRelationship(schoolid5,user.getXuehao(),school.getCity(),"0;0;0;0;0;0;0;0;0;0");
				logger.info("registerRelationship    [res1]:"+res1);
				logger.info("注册成功!");
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
		logger.info("账户已经注册");
		return ResultVoGenerator.error(ResultCode.ACCOUNT_HAS_REGISTERD);
	}

	/**
	 *
	 * @return 返回联系人分组的组名列表
	 */
	@RequestMapping(value ="/getContactGroup",method=RequestMethod.POST)
	public ResultVO getContactGroup() throws DataAccessException{
		logger.info("------------------获得联系人分组信息--------------------------");
		UserInfo user = (UserInfo)SessionUtil.getSession(Constants.USER);

		UserRelationshipWithBLOBs userRelationshipWithBLOBs =userRelationshipService.getUserRelationship(user.getUserid());
		String list = userRelationshipWithBLOBs.getGrouplist();
		logger.info(list);
		ArrayList<String> res = StringUtil.split(list,"\\10");
		return ResultVoGenerator.success(res);
	}

	/**
	 *
	 * @return 返回联系人
	 */
	@RequestMapping(value ="/getContactList",method=RequestMethod.POST)
	public ResultVO getContactList(){
		logger.info("------------------获得联系人列表--------------------------");
		UserInfo user = (UserInfo)SessionUtil.getSession(Constants.USER);
		UserRelationshipWithBLOBs userRelationshipWithBLOBs =userRelationshipService.getUserRelationship(user.getUserid());
		String list = userRelationshipWithBLOBs.getContactlist();
		logger.info(list);
		ArrayList<String> res = StringUtil.split(list,"\\10");
		return ResultVoGenerator.success(res);
	}

	/**
	 * 个人信息界面加载时，获取用户信息
	 * @return
	 * @throws DataAccessException
	 * @throws ParameterException
	 */
	@RequestMapping(value ="/getUserInfo",method=RequestMethod.POST)
	public ResultVO getUser() throws DataAccessException,ParameterException{
		logger.info("------------------获得个人信息--------------------------");
		UserInfo user = (UserInfo)SessionUtil.getSession(Constants.USER);

		user.setPassword(null);
		String schoolName = schoolService.getSchoolById5(user.getSchoolid5()).getSchoolname();

		user.setSchoolid5(schoolName);	//将传回前端的schoolid5编程schoolName
		return ResultVoGenerator.success(user);
	}

	/**
	 * 修改个人信息，不包括兴趣标签
	 * @return
	 * @throws DataAccessException
	 * @throws ParameterException
	 */
	@RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
	public ResultVO updateUser(@RequestBody UserUpdateVO userInfo) throws DataAccessException,ParameterException{
		logger.info("------------------更改个人信息--------------------------");
		UserInfo user = (UserInfo)SessionUtil.getSession(Constants.USER);
		userService.updateUserInfo(userInfo,user.getUserid());
		return ResultVoGenerator.success();
	}
}
