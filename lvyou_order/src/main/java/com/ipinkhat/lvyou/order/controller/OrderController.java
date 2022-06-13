package com.ipinkhat.lvyou.order.controller;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ipinkhat.lvyou.common.entity.PageResult;
import com.ipinkhat.lvyou.common.entity.Result;
import com.ipinkhat.lvyou.common.exception.CommonException;
import com.ipinkhat.lvyou.domain.Order;
import com.ipinkhat.lvyou.order.jwt.JwtUtils;
import com.ipinkhat.lvyou.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private JwtUtils jwtUtils;
	@RequestMapping(value = "/findByOutTradeNo/{out_trade_no}",method = RequestMethod.GET)
	public Order findByOutTradeNo(@PathVariable("out_trade_no")String out_trade_no){
		return orderService.findByOutTradeNo(out_trade_no);
	}
	@RequestMapping(value = "/{rid}/{uid}",method = RequestMethod.GET)
	public Order userOrder(@PathVariable("rid") Integer rid,@PathVariable("uid")Integer uid){
	return 	orderService.userOrder(uid,rid);
	}
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",orderService.findAll());
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
	public Order findOne(@PathVariable("id") Integer id){
		return orderService.findOne(id+"");
	}

	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		System.out.println(page);
		System.out.println(size);
		IPage<Order> pageList = orderService.findPage(page, size);
		return new Result(true,1000,"查询成功",new PageResult<Order>(pageList.getTotal(), pageList.getRecords() ) );
	}

	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){
		IPage<Order> pageList = orderService.findSearch(searchMap, page, size);
		return  new Result(true,1000,"查询成功",  new PageResult<Order>(pageList.getTotal(), pageList.getRecords()) );
	}

	/**
	 * 增加
	 * @param order
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Order order  ){
		orderService.add(order);
		return new Result(true,1000,"增加成功");
	}

	/**
	 * 修改
	 * @param order
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Order order,@PathVariable String id ){
		order.setId(id);
		orderService.update(order);
		return new Result(true,1000,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		orderService.delete(id);
		return new Result(true,1000,"删除成功");
	}

	//        Route route = routeService.findOne(rid);
	//        Integer sid = route.getSid();

	@RequestMapping("userOrder")
	public Result userOrder(String token, Integer rid){
		String uid  = jwtUtils.parseJWT(token).getId();
		orderService.userOrder(Integer.parseInt(uid),rid);
		return new Result(200);
	}

	@RequestMapping("/userOrderFeign/{token}/{rid}")
	public Order userOrders(@PathVariable("token") String token,@PathVariable("rid") Integer rid){
		String uid  = jwtUtils.parseJWT(token).getId();
		return 	orderService.userOrder(Integer.parseInt(uid),rid);

	}
	@GetMapping("findOrderByUid")
	public Result findOrderByUid(String  token) throws CommonException {
		if (token==null){
			throw new CommonException(201,"未登录");
		}
		Integer uid = Integer.valueOf(jwtUtils.parseJWT(token).getId());

		List<Order>data= orderService.userFindOrder(uid);
		return new Result(200,data);
	}
	@PostMapping("del")
	public Result delOrder(Integer id) throws IOException {
		orderService.delete(id);
		return new Result(200,"操作成功");
	}
	@PostMapping("cancelOrder")


	public Result cancelOrder(Integer id) throws IOException {
		orderService.cancelOrder(id);
		return new Result(200,"操作成功");
	}
	@GetMapping("findOrdersByUidAndRid")
	public Result findOrdersByUidAndRid(String token,Integer rid) throws IOException {
		String uid = jwtUtils.parseJWT(token).getId();
		List<Order>orders=   orderService.findByUidAndRid(uid,rid);
		return  new Result().ok().add(orders);
	}
}
