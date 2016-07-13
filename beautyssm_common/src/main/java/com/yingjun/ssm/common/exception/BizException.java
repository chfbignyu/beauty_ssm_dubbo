package com.yingjun.ssm.common.exception;


/**
 * 
 * 业务异常基类，所有业务异常都必须继承于此异常 定义异常时，需要先确定异常所属模块。 例如：无效用户可以定义为 [10010001]
 * 前四位数为系统模块编号，后4位为错误代码 ,唯一
 * 
 * (1)用户模块相关异常 1001 (2)商品模块相关异常 1002
 * 
 * 该类中定义一些公用的异常 1、数据库相关操作异常 9999 2、系统安全相关异常 9998
 * 
 * @author yingjun
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据库相关操作异常 9999
	 *******************************************************************************************************/

	/**
	 * 数据库操作,insert返回0
	 */
	public static final BizException DB_INSERT_RESULT_0 = new BizException(BizResultEnum.DB_INSERT_RESULT_0.getState(),
			BizResultEnum.DB_INSERT_RESULT_0.getMsg());

	/**
	 * 数据库操作,update返回0
	 */
	public static final BizException DB_UPDATE_RESULT_0 = new BizException(BizResultEnum.DB_UPDATE_RESULT_0.getState(),
			BizResultEnum.DB_UPDATE_RESULT_0.getMsg());

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final BizException DB_SELECTONE_IS_NULL = new BizException(BizResultEnum.DB_SELECTONE_IS_NULL.getState(),
			BizResultEnum.DB_SELECTONE_IS_NULL.getMsg());

	/**
	 * 系统相关异常 9998
	 *******************************************************************************************************/

	/**
	 * 系统内部错误
	 */
	public static final BizException INNER_ERROR = new BizException(BizResultEnum.INNER_ERROR.getState(), BizResultEnum.INNER_ERROR.getMsg());
	/**
	 * Token 验证不通过
	 */
	public static final BizException TOKEN_IS_ILLICIT = new BizException(BizResultEnum.TOKEN_IS_ILLICIT.getState(),
			BizResultEnum.TOKEN_IS_ILLICIT.getMsg());
	/**
	 * 会话超时 获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
	 */
	public static final BizException SESSION_IS_OUT_TIME = new BizException(BizResultEnum.SESSION_IS_OUT_TIME.getState(),
			BizResultEnum.SESSION_IS_OUT_TIME.getMsg());

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public BizException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BizException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	public enum BizResultEnum {

		DB_INSERT_RESULT_0(99990001, "db insert return 0"), 
		DB_UPDATE_RESULT_0(99990002, "db update return 0"), 
		DB_SELECTONE_IS_NULL(99990003,"db select return null"),
		
		INNER_ERROR(99980001, "系统错误"),
		TOKEN_IS_ILLICIT(99980002, "Token验证非法"),
		SESSION_IS_OUT_TIME(99980003, "会话超时");

		private int state;

		private String msg;

		BizResultEnum(int state, String msg) {
			this.state = state;
			this.msg = msg;
		}

		public int getState() {
			return state;
		}

		public String getMsg() {
			return msg;
		}

		public static BizResultEnum stateOf(int index) {
			for (BizResultEnum state : values()) {
				if (state.getState() == index) {
					return state;
				}
			}
			return null;
		}

	}

}
