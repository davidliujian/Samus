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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

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

	@RequestMapping(value="/getUserInfo",method=RequestMethod.GET)
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
				logger.info("register    [res]:"+res);
				int res1 = userRelationshipService.registerRelationship(schoolid5,user.getXuehao(),school.getCity());
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
	 * 获得推荐列表，返回推荐列表，同时设置数据库active为1，即用户查看过此次推荐
	 * @return
	 * @throws ServiceException
	 * @throws DataAccessException
	 */
	@RequestMapping(value="/getRecommendList",method=RequestMethod.POST)
	public ResultVO getRecommendList() throws ServiceException,DataAccessException{
		logger.info("------------------获得所有推荐人id--------------------------");
		UserInfo user = (UserInfo)SessionUtil.getSession(Constants.USER);
		logger.info("UserController   [SessionUtil.getSession(Constants.USER)]  :"+user);
		logger.info("UserController   [user.getUserid()]  :"+user.getUserid());
		UserRelationshipWithBLOBs userRelationshipWithBLOBs =userRelationshipService.getUserRelationship(user.getUserid());
		String list = userRelationshipWithBLOBs.getRecomendlist();
		logger.info(list);
		ArrayList<String> res = StringUtil.split(list,"\\10");
		//设置active为1
		userRelationshipService.setActive(user.getUserid());
		return ResultVoGenerator.success(res);
	}

	/**
	 * 获得推荐列表中的用户信息
	 * @param map
	 * @return
	 * @throws ParameterException
	 */
	@RequestMapping(value="/getRecommendDetail",method=RequestMethod.POST)
	public ResultVO getRecommendDetail(@RequestBody Map<String,String> map)throws ParameterException{
		logger.info("------------------获得推荐人详细信息--------------------------");
		logger.info("contactId:"+map.get("contactId"));
		UserInfo user = this.getUserInfo(map.get("contactId"));
		user.setPassword(null);
		logger.info("user:"+user);
		String schoolName = schoolService.getSchoolById5(user.getSchoolid5()).getSchoolname();
		logger.info("schoolName:"+schoolName);
		user.setSchoolid5(schoolName);	//将传回前端的schoolid5编程schoolName
		return ResultVoGenerator.success(user);
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
}
