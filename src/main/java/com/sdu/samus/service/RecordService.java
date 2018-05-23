package com.sdu.samus.service;

import com.sdu.samus.Constants;
import com.sdu.samus.dao.RecordDao;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.Pagination;
import com.sdu.samus.model.Record;
import com.sdu.samus.model.RecordKey;
import com.sdu.samus.util.Base64Util;
import com.sdu.samus.util.StringUtil;
import com.sdu.samus.vo.RecordDTO;
import com.sdu.samus.vo.RecordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

	public List<RecordDTO> ListRecords(Pagination pagination,String userid)throws ParameterException,ServiceException{
		List<Record> list = recordDao.getRecords(pagination,userid);
		List<RecordDTO> listRes = new ArrayList<>();
		for(Record r : list){

			RecordDTO rDTO = new RecordDTO();
			rDTO.setContent(r.getContent());
			rDTO.setCreatetime(r.getCreatetime());
			rDTO.setRecordid(r.getRecordid());
			rDTO.setUserid(r.getUserid());

			ArrayList<String> base64List = new ArrayList<String>();
			for(String s: StringUtil.split(r.getPicture(),";")){
//				logger.info(Base64Util.ImageToBase64String(s));
				base64List.add(Base64Util.ImageToBase64String(s));
			}

			rDTO.setPicture(base64List);

			listRes.add(rDTO);
		}
		return listRes;
	}

	public Record getRecord(RecordKey key){
		return recordDao.getRecord(key);
	}

	public int deleteRecord(String userId,String recordId) throws ParameterException,ServiceException{
		//判断参数是否异常
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(recordId)){
			pe.addError(ResultCode.RECORD_EMPTY);
		}
		if(pe.hasErrors()){
			throw pe;
		}
		RecordKey key = new RecordKey();
		key.setUserid(userId);
		key.setRecordid(Integer.parseInt(recordId));

		Record record = this.getRecord(key);
		String pic = record.getPicture();

		int result = recordDao.deleteRecord(key);
		if(result == 0){
			throw new ServiceException(ResultCode.DELETE_ERROR);
		}else{		//删除文件系统的图片
			File f = null;
			for(String s : StringUtil.split(pic,";")){
				//删除文件
				File file = new File(s);
				if(f == null){
					f = file.getParentFile();
				}
				if(file.exists()){
					file.delete();
				}
			}
			//判断这个文件夹是否为空，为空则删除
			if(f.listFiles().length == 0){
				f.delete();
			}
		}
		return  result;
	}
}
