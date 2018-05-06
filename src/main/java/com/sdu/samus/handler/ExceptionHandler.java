package com.sdu.samus.handler;


import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.util.ResultVoGenerator;
import com.sdu.samus.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class ExceptionHandler {

	private static final Logger _log = LoggerFactory.getLogger(ExceptionHandler.class);

	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultVO handlerParameterException(Exception e) {
		_log.info("exception = {}", e.getMessage());
		e.printStackTrace();
		if (e instanceof DataAccessException) {
			_log.info("DataAccessException");
			return ResultVoGenerator.error(ResultCode.DB_EXCEPTION);
		} else if (e instanceof ParameterException) {
			List<ResultCode> errors = ((ParameterException) e).getErrors();
			ResultVO resultVo = null;
			if (errors.size() == 1) {
				resultVo = ResultVoGenerator.error(errors.get(0));
			}
			_log.info("ParameterException, resultVo = {}", resultVo);
			return resultVo;
		} else if (e instanceof ServiceException) {
			_log.info("ServiceException");
			return ResultVoGenerator.error(((ServiceException) e).getResultCode());
		} else {
			_log.info("unknow error = {}", e.getMessage());
			return ResultVoGenerator.error(ResultCode.UNKENOW_ERROR);
		}
	}
}