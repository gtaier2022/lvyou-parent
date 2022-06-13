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
public class Comment {

@TableId
	private String id;//主键



	private Integer uid;//用户id
	private String word;//发表的语言
	private Date createTime;//创建的时间
	private String lookStatus;//可见状态，默认是0，异常是1 删除是2
	private Integer rid;//路线id
	private String name;//


}
