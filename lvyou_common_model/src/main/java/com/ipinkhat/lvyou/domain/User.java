package com.ipinkhat.lvyou.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
public class User {

	@TableId
	private String uid;//主键用户id



	private String telephone;//电话
	private String password;//密码
	private String name;//账号名 不是账号
	private String sex;//性别
	private String email;//邮箱
	private String status;//使用状态
	private String age;//年龄
	private String headImg;//头像
	private String username;//用户账号


}
