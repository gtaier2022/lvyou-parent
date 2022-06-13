package com.ipinkhat.lvyou.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
public class Seller {

	@TableId
	private String  sid;//主键



	private String sname;//公司名称
	private String telephone;//联系电话
	private String address;//公司地址
	private String type;//公司类型
	private String checkState;//审核状态
	private String license;//公司营业许可证
	private Date registerTime;//公司注册时间
	private String email;//公司邮箱
	private String password;//密码
	private String advise;//


}
