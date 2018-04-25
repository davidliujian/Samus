package com.sdu.samus.util;

import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.vo.ResultVO;

public class ResultVoGenerator {

	public static ResultVO success() {
		return success(null);
	}

	public static ResultVO error(ResultCode resultCode) {
		ResultVO resultVo = new ResultVO.Builder()
				.code(resultCode.getCode())
				.msg(resultCode.getMsg())
				.data(null)
				.build();
		return resultVo;
	}

	public static ResultVO success(Object data) {
		ResultVO resultVo = new ResultVO.Builder()
				.code(ResultCode.SUCCESS.getCode())
				.msg(ResultCode.SUCCESS.getMsg())
				.data(data)
				.build();
		return resultVo;
	}
}