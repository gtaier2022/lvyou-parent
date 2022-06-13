package com.ipinkhat.lvyou.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
public class Order {

	@TableId
	private String id;//主键



	private Date outTradeDate;//订单时间
	private String outTradeNo;//订单编号
	private String subject;//订单名称
	private Long totalAmount;//金额
	private String body;//商品描述
	private Integer rid;//路线id
	private Integer uid;//用户id
	private Integer status;//订单状态 0表式未支付 1表示已经支付
	private String look;//订单的可见性 0表示用户商家都可见，1表示都用户不可见商家可见，2表示商家和用户都不可见，3仅仅系统可见
	private String sid;//商家id
	private Integer serviceStatus;//0:表示未服务 用户不可评论 1表示用户已被服务:开启用户评论，开启商户完成服务按钮
	@TableField(exist = false)
	private Seller seller;
	@TableField(exist = false)
	private Route route;

}
