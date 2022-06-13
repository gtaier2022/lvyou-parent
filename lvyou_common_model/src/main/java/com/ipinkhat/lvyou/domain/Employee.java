package com.ipinkhat.lvyou.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
public class Employee {

@TableId
	private String id;//
	private String username;//
	private String password;//
	private String role;//
	private String name;//

}
