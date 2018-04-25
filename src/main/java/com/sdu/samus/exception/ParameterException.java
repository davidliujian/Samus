package com.sdu.samus.exception;

import com.sdu.samus.enums.ResultCode;

import java.util.ArrayList;
import java.util.List;

/**
 *  参数异常类，声明一个list存放错误
 */
public class ParameterException extends Exception{

	private List<ResultCode> errors = new ArrayList<ResultCode>();

	public List<ResultCode> getErrors(){
		return errors;
	}

	public void addError(ResultCode resultCode){
		errors.add(resultCode);
	}

	public boolean hasErrors(){
		return !errors.isEmpty();
	}

	public void clearErrors(){
		errors.clear();
	}
}
