package com.ipinkhat.seller.controller;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ipinkhat.lvyou.common.entity.PageResult;
import com.ipinkhat.lvyou.common.entity.Result;
import com.ipinkhat.lvyou.domain.Seller;
import com.ipinkhat.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",sellerService.findAll());
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Seller findOne(@PathVariable String id){
		return sellerService.findOne(id);
	}

	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		IPage<Seller> pageList = sellerService.findPage(page, size);
		return new Result(true,1000,"查询成功",new PageResult<Seller>(pageList.getTotal(), pageList.getRecords() ) );
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
		IPage<Seller> pageList = sellerService.findSearch(searchMap, page, size);
		return new Result(true,1000,"查询成功",new PageResult<Seller>(pageList.getTotal(), pageList.getRecords() ) );

	}

	/**
	 * 增加
	 * @param seller
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Seller seller  ){
		sellerService.add(seller);
		return new Result(true,1000,"增加成功");
	}

	/**
	 * 修改
	 * @param seller
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Seller seller,@PathVariable String id ){
		seller.setSid(id);
		sellerService.update(seller);
		return new Result(true,1000,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		sellerService.delete(id);
		return new Result(true,1000,"删除成功");
	}

}
