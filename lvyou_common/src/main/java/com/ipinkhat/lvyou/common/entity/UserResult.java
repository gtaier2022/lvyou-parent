package com.ipinkhat.lvyou.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResult {


	private boolean flag;
	private Integer code;
	private String token;




	public UserResult(boolean flag, Integer code, String token) {
		this.token=token;
		this.flag = flag;
		this.code = code;
	}

}
