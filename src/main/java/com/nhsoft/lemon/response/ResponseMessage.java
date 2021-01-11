/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.nhsoft.lemon.response;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author wanglei
 */
@Data
public class ResponseMessage {
	private static final long serialVersionUID = 1L;
	/**
	 * 响应代码
	 */
	private int code;

	/**
	 * 响应消息
	 */
	private String message;

	/**
	 * 响应结果
	 */
	private Object result;

	public ResponseMessage() {
	}

	public ResponseMessage(int code, String message, Object result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	/**
	 * 成功时响应
	 * @param data
	 * @return
	 */
	public static ResponseMessage success(Object data) {
		ResponseMessage resp = new ResponseMessage();
		resp.setCode(StatusEnum.SUCCESS.getCode());
		resp.setMessage(StatusEnum.SUCCESS.getMessage());
		resp.setResult(data);
		return resp;
	}

	public static ResponseMessage success(String msg,Object data) {
		ResponseMessage resp = new ResponseMessage();
		resp.setCode(StatusEnum.SUCCESS.getCode());
		resp.setMessage(msg);
		resp.setResult(data);
		return resp;
	}
	/**
	 * 失败响应
	 * @return
	 */
	public static ResponseMessage error(){
		ResponseMessage resp = new ResponseMessage();
		resp.setCode(StatusEnum.SUCCESS.getCode());
		resp.setMessage(StatusEnum.SUCCESS.getMessage());
		resp.setResult(null);
		return resp;
	}

	public static ResponseMessage error(int code, String message) {
		ResponseMessage resp = new ResponseMessage();
		resp.setCode(code);
		resp.setMessage(message);
		resp.setResult(null);
		return resp;
	}

	public static ResponseMessage error(String message) {
		ResponseMessage resp = new ResponseMessage();
		resp.setCode(-1);
		resp.setMessage(message);
		resp.setResult(null);
		return resp;
	}

}
