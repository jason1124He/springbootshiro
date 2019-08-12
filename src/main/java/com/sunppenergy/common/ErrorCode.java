package com.sunppenergy.common;


/**
 * Created by momo on 2016/8/17. AJAX 错误号，理论上需要按照： 错误号{5位} = 模块编号{2} + 错误编号{3}
 * 这里从110开始往后递增编号就可以了，
 */
public enum ErrorCode {
	CODE("未定义错误",110), 
	Success("success",200),
	NO_LOGIN("noLogin",401),
	NO_TOKEN("notoken",402),
	TOURIST("tourist",403),
	DATA_FAIL("DATA_FAIL",206),
	DATA_NULL("DATA_NULL",204),
	NO_POWER("no_power",120);
	
	private int code;;

	private String msg;

	private ErrorCode(String msg, int code) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
