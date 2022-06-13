package com.ipinkhat.lvyou.common.entity;

import com.ipinkhat.lvyou.domain.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Result {


	private boolean flag;
	private Integer code;
	private String message;
	private Object data;




	public Result(boolean flag, Integer code, String message, Object data) {
		super();
		this.flag = flag;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public Result(Integer code) {
		super();
		this.code = code;
	}

	public Result(Integer code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}
	public Result(boolean flag, Integer code, String message) {
		super();
		this.flag = flag;
		this.code = code;
		this.message = message;
	}
	public Result(boolean flag, Integer code, Object data) {
		super();
		this.flag = flag;
		this.code = code;
		this.data = data;
	}

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
public Result ok() {
		this.flag = true;
		this.code = 200;
		return this;
}

	public Result fail() {
		this.flag = false;
		this.code = 201;
		return this;
	}
	public Result sendMsg(String message) {
	this.message = message;
		return this;
	}

	public Result add(Object data) {
		this.data =data;
		return this;
	}
}
