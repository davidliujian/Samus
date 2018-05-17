package com.sdu.samus.controller;

import com.sdu.samus.Constants;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.UserInfo;
import com.sdu.samus.model.UserRelationshipWithBLOBs;
import com.sdu.samus.service.SchoolService;
import com.sdu.samus.service.UserRelationshipService;
import com.sdu.samus.service.UserService;
import com.sdu.samus.util.ResultVoGenerator;
import com.sdu.samus.util.SessionUtil;
import com.sdu.samus.util.StringUtil;
import com.sdu.samus.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/relation")
public class UserRelationshipController {

	@Autowired
	private UserService userService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private UserRelationshipService userRelationshipService;

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

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

		//设置active为1
		userRelationshipService.setActive(user.getUserid());

		if(StringUtil.isEmpty(list)){
			return ResultVoGenerator.success();
		}else{
			ArrayList<String> res = StringUtil.split(list,";");
			return ResultVoGenerator.success(res);
		}
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
		UserInfo user = userService.getUserInfo(map.get("contactId"));
		user.setPassword(null);
		logger.info("user:"+user);
		String schoolName = schoolService.getSchoolById5(user.getSchoolid5()).getSchoolname();
		logger.info("schoolName:"+schoolName);
		user.setSchoolid5(schoolName);	//将传回前端的schoolid5编程schoolName
		return ResultVoGenerator.success(user);
	}

	@RequestMapping(value = "/dislike",method=RequestMethod.POST)
	public ResultVO leftDislike(@RequestBody Map<String,String> map)throws ParameterException,ServiceException{
		logger.info("------------------左滑不喜欢推荐人--------------------------");
		String userId = map.get("contactId");
		logger.info("contactId:"+map.get("contactId"));

		UserInfo user = (UserInfo)SessionUtil.getSession(Constants.USER);
		userRelationshipService.updateDislike(user.getUserid());
		return ResultVoGenerator.success();
	}

	@RequestMapping(value = "/like",method = RequestMethod.POST)
	public ResultVO rightLike(@RequestBody Map<String,String> map)throws ParameterException,ServiceException{
		logger.info("------------------右滑喜欢推荐人--------------------------");
		String contactId = map.get("contactId");
		logger.info("contactId:"+map.get("contactId"));

		UserInfo user = (UserInfo)SessionUtil.getSession(Constants.USER);
		userRelationshipService.updateLike(user.getUserid(),contactId);
		return ResultVoGenerator.success();
	}
}
