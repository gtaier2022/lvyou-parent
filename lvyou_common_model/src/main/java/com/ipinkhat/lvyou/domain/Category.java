package com.ipinkhat.lvyou.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
public class Category {
	@TableId
	private Integer cid;//主键类型id
	private String cname;//类型名称
}
