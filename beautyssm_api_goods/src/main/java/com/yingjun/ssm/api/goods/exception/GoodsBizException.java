package com.yingjun.ssm.api.goods.exception;

import com.yingjun.ssm.common.exception.BizException;

public class GoodsBizException extends BizException{

	private static final long serialVersionUID = 1L;
	
	public GoodsBizException() {
	}

	public GoodsBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public GoodsBizException(int code, String msg) {
		super(code, msg);
	}
	
}
