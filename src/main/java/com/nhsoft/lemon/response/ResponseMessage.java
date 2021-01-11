/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.nhsoft.lemon.response;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author wanglei
 */
public class ResponseMessage extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public ResponseMessage() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static ResponseMessage error() {
		return error(StatusEnum.FAIL.getCode(), StatusEnum.FAIL.getMessage());
	}
	
	public static ResponseMessage error(String msg) {
		return error(StatusEnum.FAIL.getCode(), msg);
	}
	
	public static ResponseMessage error(int code, String msg) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.put("code", code);
		responseMessage.put("msg", msg);
		return responseMessage;
	}

	public static ResponseMessage ok(String msg) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.put("msg", msg);
		return responseMessage;
	}
	
	public static ResponseMessage ok(Map<String, Object> map) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.putAll(map);
		return responseMessage;
	}
	
	public static ResponseMessage ok() {
		return new ResponseMessage();
	}

	public ResponseMessage put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
