package com.sdu.samus.controller;

import com.sdu.samus.Constants;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.model.UserInfo;
import com.sdu.samus.service.RecordService;
import com.sdu.samus.util.ResultVoGenerator;
import com.sdu.samus.util.SessionUtil;
import com.sdu.samus.vo.RecordVO;
import com.sdu.samus.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/record")
public class RecordController {

	@Autowired
	RecordService recordService;
	private static final Logger logger = LoggerFactory.getLogger(RecordController.class);

	@RequestMapping(value = "/publish",method = RequestMethod.POST)
	public ResultVO publishRecord(@RequestBody RecordVO record) throws ParameterException {
		logger.info("----------------发布动态-----------------------------");
		UserInfo user =(UserInfo) SessionUtil.getSession(Constants.USER);
		recordService.insertRecord(record,user.getUserid());
		return ResultVoGenerator.success();
	}


}