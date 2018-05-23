package com.sdu.samus.controller;

import com.sdu.samus.Constants;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.School;
import com.sdu.samus.model.UserInfo;
import com.sdu.samus.model.UserLog;
import com.sdu.samus.model.UserRelationshipWithBLOBs;
import com.sdu.samus.service.SchoolService;
import com.sdu.samus.service.UserLogService;
import com.sdu.samus.service.UserRelationshipService;
import com.sdu.samus.service.UserService;
import com.sdu.samus.util.ResultVoGenerator;
import com.sdu.samus.util.SessionUtil;
import com.sdu.samus.util.SimulateLoginUtil;
import com.sdu.samus.util.StringUtil;
import com.sdu.samus.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
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
	@Autowired
	private UserLogService userLogService;

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

		//生成logid
		int logid = StringUtil.generateIntRandom(7);
		//插入userlog表
		UserLog userLog = new UserLog();
		userLog.setLogid(logid);
		userLog.setUserid(user.getUserId());
		Timestamp time1 = new Timestamp(System.currentTimeMillis());
		userLog.setLogintime(time1);
		userLogService.insertLog(userLog);
		logger.info("插入日志成功！！！");

		//保存用户session
		userInfo.setPassword(null);
		SessionUtil.addSession(Constants.USER, userInfo);
		//保存logid进session
		SessionUtil.addSession(Constants.LOG_ID,logid);

		return ResultVoGenerator.success();

	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResultVO register(@RequestBody UserRegisterVO user) throws ParameterException,ServiceException,DataAccessException,IOException {
		logger.info("------------------注册--------------------------");
		logger.info(user.getSchoolId5()+"  "+user.getXuehao()+"    "+user.getGender()+"    "+user.getPassword());
		//根据学校名获取学校
		School school = schoolService.getSchoolBySchoolId5(user);

		//验证学生身份，模拟登陆教务系统
		String isStu = SimulateLoginUtil.simulate(user.getXuehao(),user.getPassword(),school.getUrl());
		if(!"success".equals(isStu.substring(isStu.indexOf("\"")+1,isStu.lastIndexOf("\"")))){
			return ResultVoGenerator.error(ResultCode.NOT_STUDENT);
		}

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

	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public ResultVO logout(){
		logger.info("------------------退出-------------------------");
		UserInfo user = (UserInfo)SessionUtil.getSession(Constants.USER);
		//update UserLog表
		int logid = (Integer)SessionUtil.getSession(Constants.LOG_ID);
		UserLog userLog = new UserLog();
		userLog.setLogid(logid);
		userLog.setUserid(user.getUserid());
		Timestamp time1 = new Timestamp(System.currentTimeMillis());
		userLog.setLogouttime(time1);
		userLogService.updateLog(userLog);
		logger.info("修改日志的logouttime成功！！！");
		//清除session
		SessionUtil.removeSession(Constants.USER);
		SessionUtil.removeSession(Constants.LOG_ID);
		return ResultVoGenerator.success();
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
		if(StringUtil.isEmpty(list)){
			return ResultVoGenerator.success();
		}else {
			ArrayList<String> res = StringUtil.split(list, "\\10");
			return ResultVoGenerator.success(res);
		}
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
		if(StringUtil.isEmpty(list)){
			return ResultVoGenerator.success();
		}else {
			ArrayList<String> res = StringUtil.split(list, "\\10");
			ArrayList<Contact> result = new ArrayList<>();

			//修改返回的联系人以及其分组的格式
			for (String s : res) {
				Contact con = new Contact(s.substring(0, s.indexOf(':')), s.substring(s.indexOf(':')+1,s.lastIndexOf(':')),s.substring(s.lastIndexOf(':') + 1));
				result.add(con);
			}

			return ResultVoGenerator.success(result);
		}
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
		UserInfo userInfo = userService.getUserInfo(user.getUserid());
		UserGetVO userGetVO = UserGetVO.UserInfoToUserGetVO(userInfo);

		String schoolName = schoolService.getSchoolById5(user.getSchoolid5()).getSchoolname();
		userGetVO.setSchoolName(schoolName);
//		logger.info("头像"+userGetVO.getAvatar());
		return ResultVoGenerator.success(userGetVO);
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
//		logger.info("更改头像："+userInfo.getAvatar());
		userService.updateUserInfo(userInfo,user.getUserid());
		return ResultVoGenerator.success();
	}

	@RequestMapping(value = "/updateHobby",method = RequestMethod.POST)
	public ResultVO updateHobby(@RequestBody HobbyVO hobbyVO) throws ParameterException,ServiceException{
		logger.info("------------------更改个人兴趣标签--------------------------");
		logger.info("提交的音乐为："+hobbyVO.getMusic());
		UserInfo user = (UserInfo) SessionUtil.getSession(Constants.USER);
		userService.updateHobby(hobbyVO,user.getUserid());
		return ResultVoGenerator.success();
	}

	//用于返回联系人列表时，每个人的name以及group
	class Contact{
		private String contactId;
		private String name;
		private String group;

		public Contact(){}

		public Contact(String contactId,String name,String group){
			this.contactId = contactId;
			this.name = name;
			this.group = group;
		}

		public String getContactId() {
			return contactId;
		}

		public String getName() {
			return name;
		}

		public String getGroup() {
			return group;
		}

	}
}