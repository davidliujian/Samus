package com.sdu.samus.service;

import com.sdu.samus.Constants;
import com.sdu.samus.dao.RecordDao;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.Record;
import com.sdu.samus.util.Base64Util;
import com.sdu.samus.util.StringUtil;
import com.sdu.samus.vo.RecordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

	@Autowired
	RecordDao recordDao;

	private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

	public int insertRecord(RecordVO recordVO,String userId) throws ParameterException {
		Record record = new Record();
		StringBuffer sb = new StringBuffer();

		//对图片进行保存在服务器的文件夹中
		for(String base64 : recordVO.getPicture()){
			String path = Constants.IMG_PATH+userId+"\\"+StringUtil.generateRandom(5)+".jpg";
			logger.info(path+"");

			// 去掉base64数据头部data:image/png;base64,和尾部的” " “
			String[] ww= base64.split(",");
			base64 = ww[1];
			String[] aa = base64.split("\"");
			base64 = aa[0];

			Base64Util.base64StringToImage(base64,path,"jpg");
			sb.append(path).append(";");
		}
		sb.deleteCharAt(sb.lastIndexOf(";"));

		String pic = sb.toString();
		logger.info(pic+"");
		record.setUserid(userId);
		record.setContent(recordVO.getContent());
		record.setPicture(pic);

		int result  = recordDao.insertRecord(record);

		if(result == 0){
			throw new ServiceException(ResultCode.PUBLISH_WRONG);
		}
		return  result;
	}
}
