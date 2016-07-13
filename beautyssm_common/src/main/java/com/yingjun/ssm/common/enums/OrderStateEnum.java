package com.yingjun.ssm.common.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author yingjun
 *
 */
public enum OrderStateEnum {

	OUT("下架", 0), NORMAL("正常", 1);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private OrderStateEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static OrderStateEnum getEnum(int value) {
		OrderStateEnum resultEnum = null;
		OrderStateEnum[] enumAry = OrderStateEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	/**
	 * 枚举转map.<br/>
	 * @return
	 */
	public static Map<String, Map<String, Object>> toMap() {
		OrderStateEnum[] ary = OrderStateEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key,map);
		}
		return enumMap;
	}

}
