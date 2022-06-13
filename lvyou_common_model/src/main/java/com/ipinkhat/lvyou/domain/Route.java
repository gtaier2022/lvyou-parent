package com.ipinkhat.lvyou.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
public class Route {

	@TableId
	private String rid;//
	private String rname;//
	private Long price;//
	private String routeIntroduce;//
	private String rflag;//
	private String rdate;//
	private String isThemeTour;//
	private Integer count;//
	private Integer cid;//
	private String rimage;//
	private Integer sid;//
	private String sourceId;//
	private String status;//
	private String advise;//
	@TableField(exist = false)
	private List<RouteImg> routeImgs;
	@TableField(exist = false)
	private Category category;
	@TableField(exist = false)
	private Seller seller;
	@TableField(exist = false)
	private List<Comment>commentList;

}
