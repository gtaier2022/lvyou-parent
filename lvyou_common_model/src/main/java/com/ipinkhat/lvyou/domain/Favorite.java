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
public class Favorite {

@TableId
	private String fid;//收藏主键



	private Integer rid;//收藏路线id
	private Date date;//创建时间
	private Integer uid;//用户id


}
