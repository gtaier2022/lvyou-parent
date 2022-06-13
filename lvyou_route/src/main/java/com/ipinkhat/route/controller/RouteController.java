package com.ipinkhat.route.controller;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipinkhat.lvyou.common.entity.PageResult;
import com.ipinkhat.lvyou.common.entity.Result;
import com.ipinkhat.lvyou.domain.*;
import com.ipinkhat.route.feign.CategoryFeginClient;
import com.ipinkhat.route.feign.CommentFeginClient;
import com.ipinkhat.route.feign.FavoriteFeignClient;
import com.ipinkhat.route.feign.SellerFeginClient;
import com.ipinkhat.route.jwt.JwtUtils;
import com.ipinkhat.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * 控制器层
 * @author Administrator
 *	收藏路线和取消收藏路线
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/route")
public class RouteController {

	@Autowired
	private RouteService routeService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	CategoryFeginClient categoryFeginClient;
	@Autowired
	SellerFeginClient sellerFeginClient;
	@Autowired
	CommentFeginClient commentFeginClient;
	@Autowired
	FavoriteFeignClient favoriteFeignClient;
	/**
	 * 查询全部数据
	 * @return
	 */

	@RequestMapping(value="/",method=RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",routeService.findAll());
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Route findOne(@PathVariable String id){
		return routeService.findOne(id);
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
		IPage<Route> pageList = routeService.findPage(page, size);
		return new Result(true,1000,"查询成功",pageList );
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
		IPage<Route> pageList = routeService.findSearch(searchMap, page, size);
		return new Result(true,1000,"查询成功",new PageResult<Route>(pageList.getTotal(), pageList.getRecords() ) );		}

	/**
	 * 增加
	 * @param route
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Route route  ){
		routeService.add(route);
		return new Result(true,1000,"增加成功");
	}

	/**
	 * 修改
	 * @param route
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Route route,@PathVariable String id ){
		route.setRid(id);
		routeService.update(route);
		return new Result(true,1000,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		routeService.delete(id);
		return new Result(true,1000,"删除成功");
	}
	@GetMapping("list")
	public Result getRouteByCid(Integer cid, Integer current, Integer size, String rname) {
		if (rname == null) {
			if (current == null) {
				current=1;
			}
			IPage<Route> data = routeService.getRouteByCid(cid, current, size);
			return new Result(true, 200,data);
		}
		else  if (cid==null){
			System.out.println("cid ==null");
			if (current == null) {
				current=1;
			}
			IPage<Route> data = routeService.getRouteByRname( current, size,rname);
			List<Route> records = data.getRecords();
			for (Route record : records) {
				System.out.println(record);
			}
			return new Result(200, data);
		}
		else  {
			IPage<Route>  data = routeService.getRouteByCidAndRName(cid, size, current, rname);
			return new Result(200, data);
		}


	}


	@GetMapping("findOne")
	public Result findOne( Integer rid) {
		Route route = routeService.findById(rid);
		Seller seller = sellerFeginClient.findById(route.getSid());
		route.setSeller(seller);
		Category category = categoryFeginClient.findById(route.getCid());
		route.setCategory(category);
		List<Comment> comments = commentFeginClient.findById(rid);
		route.setCommentList(comments);
		return new Result(true,200, route);
	}

	//首页主题旅游
	@GetMapping("isThemeTour")
	public Result isThemeTour(){

		List<Route>data =   routeService.findThemeTour();
		return  new Result(200,data);
	}
	//首页人气旅游
	//收藏数量最多的旅游

	@GetMapping("pop")
	public Result pop(){
		List<Route> data = routeService.pop();
		return  new Result(200,data);
	}
	//最新旅游
	@GetMapping("new")
	public Result news() {
		List<Route> data = routeService.news();
		return new Result(200, data);
	}
	//首页国内游
	@GetMapping("firstPageCN")
	public Result firstPageCN(){
		List<Route> data = routeService.firstPageCN();
		return new Result(200, data);
	}
	//首页国内游

	@GetMapping("firstPageFN")
	public Result firstPageFN(){
		List<Route> data = routeService.firstPageFN();
		return new Result(200, data);
	}
	//详情页面推荐热门推荐

	@GetMapping("hot")
	public Result hot(){
		List<Route> data = routeService.hot();
		return new Result(200, data);
	}



	//我的收藏路线
	@GetMapping("myfavroute")
	public Result myFavRoute(String token , Integer current, Integer size){

		Integer uid = Integer.valueOf(jwtUtils.parseJWT(token).getId());
		List<Favorite> list = favoriteFeignClient.findById(uid);
		Page<Route> data =  routeService.myFavRoute(uid,current,size,list);
		if (data==null){
			return  new Result().fail().sendMsg("您还没有收藏");
		}
		return new Result(200,data);
	}
	//收藏排行板搜索按照要求的数据


	@GetMapping("shousuo")
	public Result shouSuo(Integer current,Integer size,String rname,Integer lowPirce,Integer heightPrice){
		Page<Route>data =  routeService.shouSuo(current,size,rname,lowPirce,heightPrice);
		return new Result(200,data);
	}

}
