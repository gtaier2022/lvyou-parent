package com.ipinkhat.user.controller;
import java.util.Map;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ipinkhat.lvyou.common.entity.PageResult;
import com.ipinkhat.lvyou.common.entity.Result;
import com.ipinkhat.lvyou.common.entity.UserResult;
import com.ipinkhat.lvyou.common.exception.CommonException;
import com.ipinkhat.lvyou.domain.User;
import com.ipinkhat.user.jwt.JwtUtils;
import com.ipinkhat.user.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private JwtUtils jwtUtils;
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",userService.findAll());
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public User findOne(@PathVariable String id){
		return userService.findOne(id);
	}

	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		IPage<User> pageList = userService.findPage(page, size);
		return  new Result(true,1000,"查询成功",  new PageResult<User>(pageList.getTotal(), pageList.getRecords()) );
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
		IPage<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,1000,"查询成功",  new PageResult<User>(pageList.getTotal(), pageList.getRecords()) );
	}

	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,1000,"增加成功");
	}

	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody User user,@PathVariable String id ){
		user.setUid(id);
		userService.update(user);
		return new Result(true,1000,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.delete(id);
		return new Result(true,1000,"删除成功");
	}

	/**
	 * 用户注册
	 * @param user
	 * @param code
	 * @return
	 */
	@RequestMapping("register")
	public Result register( User user,String code){
		String telephone = user.getTelephone();
		String rCode = redisTemplate.boundValueOps(telephone).get();
		if (!rCode.equals(code)){
			return new Result(false,201,"验证码不正确");
		}
		String username = user.getUsername();
		User u = userService.findByUsername(username);
		if (u!=null){
			return  new Result(false,201,"该账号已被注册");
		}else {
			userService.insert(user);

			return  new Result(true,200,"注册成功");
		}
	}

	@RequestMapping("login")
	public UserResult login(String username, String password) throws CommonException {
		System.out.println(username);
		System.out.println(password);
		User u = userService.findByUsername(username);
		if (u==null||!u.getPassword().equals(password)){
			throw new CommonException(201,"账号密码错误，请重试");
		}
		System.out.println(u.getUid());
		String token = jwtUtils.createJWT(u.getUid() + "", u.getUsername(), null);
		System.out.println(token);
		return new UserResult(true,200,token);
	}
	@RequestMapping("info")
	public Result  info(String token){
		System.out.println(token);
		if (token.equals("")){
			return new Result();
		}
		Claims claims = jwtUtils.parseJWT(token);

		String id = claims.getId();
		System.out.println(id);
		User u = userService.findByUid(Integer.parseInt(id));
		return new Result().ok().add(u);
	}

}
