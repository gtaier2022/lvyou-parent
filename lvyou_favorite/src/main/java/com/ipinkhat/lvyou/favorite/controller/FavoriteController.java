package com.ipinkhat.lvyou.favorite.controller;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ipinkhat.lvyou.common.entity.PageResult;
import com.ipinkhat.lvyou.common.entity.Result;
import com.ipinkhat.lvyou.domain.Favorite;
import com.ipinkhat.lvyou.domain.Route;
import com.ipinkhat.lvyou.favorite.feign.RouteFeginClient;
import com.ipinkhat.lvyou.favorite.jwt.JwtUtils;
import com.ipinkhat.lvyou.favorite.producer.MessageSender;
import com.ipinkhat.lvyou.favorite.service.FavoriteService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/favorite")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	MessageSender messageSender;
	@Autowired
	RouteFeginClient routeFeginClient;
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",favoriteService.findAll());
	}


	@RequestMapping(value="/findByUid/{uid}",method=RequestMethod.GET)
	public List<Favorite> findByUid(@PathVariable("uid")Long uid){
		return favoriteService.findByUid(uid);
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Result findOne(@PathVariable String id){
		return new Result(true,1000,"查询成功",favoriteService.findOne(id));
	}

	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		IPage<Favorite> pageList = favoriteService.findPage(page, size);
		return new Result(true,1000,"查询成功",new PageResult<Favorite>(pageList.getTotal(), pageList.getRecords()) );
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
		IPage<Favorite> pageList = favoriteService.findSearch(searchMap, page, size);
		return  new Result(true,1000,"查询成功",  new PageResult<Favorite>(pageList.getTotal(), pageList.getRecords()) );
	}

	/**
	 * 增加
	 * @param favorite
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Favorite favorite  ){
		favoriteService.add(favorite);
		return new Result(true,1000,"增加成功");
	}

	/**
	 * 修改
	 * @param favorite
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Favorite favorite,@PathVariable String id ){
		favorite.setFid(id);
		favoriteService.update(favorite);
		return new Result(true,1000,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		favoriteService.delete(id);
		return new Result(true,1000,"删除成功");
	}
	//还要让路线的收藏数量+1
	@GetMapping("addFavorite")
	public Result addFavorite(Integer rid, String token){
		Route route = routeFeginClient.findById(rid);

		if (token==null||token.equals("")){
			return new Result().fail().sendMsg("您未登录");
		}
		Claims claims = jwtUtils.parseJWT(token);
		String uid = claims.getId();
		Favorite oneFavorite = favoriteService.findOneFavorite(Integer.parseInt(uid), rid);

		if(oneFavorite==null){
			Favorite favorite = new Favorite();
			favorite.setUid(Integer.parseInt(uid));
			favorite.setRid(rid);
			favoriteService.addFavorite(favorite);
			route.setCount(route.getCount()+1);
			messageSender.send(route);
			return new Result().ok().sendMsg("添加成功");
		}else {
			return new Result().fail().sendMsg("已经收藏");
		}
	}
	//判断该路线该人有没有收藏
	@GetMapping("findOneFavorite")
	public Result findOneFavorite(String token,  Integer rid){
		String uid = jwtUtils.parseJWT(token).getId();
		if (uid==null){
			return  new Result().fail().sendMsg("您未登录");
		}
		Favorite favorite =  favoriteService.findOneFavorite(Integer.parseInt(uid),rid);
		if (favorite==null){
			//没有被收藏;
			return new Result().ok();
		}else {
			return new Result().fail();
		}
	}
	//还要让路线收藏的数量减少1
	@PostMapping("deleteFavorite")
	public Result deleteFavorite(String token,Integer rid){
		System.out.println("取消收藏");
		Route route = routeFeginClient.findById(rid);
		route.setCount(route.getCount()-1);
		messageSender.send(route);
		Integer uid =Integer.parseInt(jwtUtils.parseJWT(token).getId());
		Favorite oneFavorite = favoriteService.findOneFavorite(uid, rid);
		favoriteService.deleteFavorite(oneFavorite.getFid());
		return  new Result(200,"取消收藏成功");
	}
	@GetMapping("findFavoriteByUId")
	public Result findFavoriteByUId(String token){
		Integer uid = Integer.valueOf(jwtUtils.parseJWT(token).getId());
		List<Favorite> list = favoriteService.findByUid(uid);
		if (list.size()==0){
			return new Result().fail().sendMsg("您未进行收藏");
		}else {
			return new Result().ok();
		}
	}
}
