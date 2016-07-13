package com.yingjun.ssm.api.user.exception;

import com.yingjun.ssm.common.exception.BizException;

/**
 * 用户模块相关异常 1001
 * 
 * @author yingju
 *
 */
public class UserBizException extends BizException {

	private static final long serialVersionUID = 1L;

	public static final UserBizException INVALID_USER = new UserBizException(UserResultEnum.INVALID_USER.getState(),
			UserResultEnum.INVALID_USER.getMsg());

	public UserBizException() {
	}

	public UserBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public UserBizException(int code, String msg) {
		super(code, msg);
	}

	public enum UserResultEnum {

		INVALID_USER(1001, "无效用户");

		private int state;

		private String msg;

		UserResultEnum(int state, String msg) {
			this.state = state;
			this.msg = msg;
		}

		public int getState() {
			return state;
		}

		public String getMsg() {
			return msg;
		}

		public static UserResultEnum stateOf(int index) {
			for (UserResultEnum state : values()) {
				if (state.getState() == index) {
					return state;
				}
			}
			return null;
		}

	}

}
