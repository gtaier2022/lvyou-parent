package com.ipinkhat.lvyou.comment.controller;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ipinkhat.lvyou.comment.feign.OrderFeginClient;
import com.ipinkhat.lvyou.comment.feign.UserFeginClient;
import com.ipinkhat.lvyou.comment.producer.MessageSender;
import com.ipinkhat.lvyou.comment.service.CommentService;
import com.ipinkhat.lvyou.common.entity.PageResult;
import com.ipinkhat.lvyou.common.entity.Result;
import com.ipinkhat.lvyou.common.exception.CommonException;
import com.ipinkhat.lvyou.common.utils.IdWorker;
import com.ipinkhat.lvyou.domain.Comment;
import com.ipinkhat.lvyou.domain.Order;
import com.ipinkhat.lvyou.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	OrderFeginClient orderFeginClient;
	@Autowired
	UserFeginClient userFeginClient;
	@Autowired
	MessageSender messageSender;
	@Autowired
	private IdWorker idWorker;
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value = "/findByLookStatus/{rid}",method = RequestMethod.GET)
	public List<Comment>  findByLookStatus(@PathVariable Long rid){
	return 	commentService.findByLookStatus(rid);
	}
	@RequestMapping(value="/",method=RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",commentService.findAll());
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Result findOne(@PathVariable String id){
		return new Result(true,1000,"查询成功",commentService.findOne(id));
	}

	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		IPage<Comment> pageList = commentService.findPage(page, size);
		return new Result(true,1000,"查询成功",new PageResult<Comment>(pageList.getTotal(), pageList.getRecords() ) );
	}

	/**
	 * 分页+多条件查询
	 * @param
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){
		IPage<Comment> pageList = commentService.findSearch(searchMap, page, size);
		return  new Result(true,200,"查询成功",  new PageResult<Comment>(pageList.getTotal(), pageList.getRecords()) );
	}

	/**
	 * 增加
	 * @param comment
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Comment comment  ){
		commentService.add(comment);
		return new Result(true,1000,"增加成功");
	}

	/**
	 * 修改
	 * @param comment
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Comment comment,@PathVariable String id ){
		comment.setId(id);
		commentService.update(comment);
		return new Result(true,1000,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		commentService.delete(id);
		return new Result(true,1000,"删除成功");
	}
	@GetMapping("/getAll")
	public IPage<Comment>findAll(Integer rid, Integer current, Integer pageSize){
		return commentService.findAllByRoute(rid,current,pageSize);
	}


	@DeleteMapping("delete")
	public Result delete(Integer cid){
		commentService.delete(cid);
		return new Result();
	}
	//跟订单放在一起
	@PostMapping("/add")
	public Result add(String word,Long id) throws CommonException {
		Order order = orderFeginClient.findById(id);
		order.setServiceStatus(2);
		Integer uid = order.getUid();
		User u = userFeginClient.findById(uid);
		messageSender.send(order);

		Comment comment = new Comment();
		comment.setId(idWorker.nextId()+"");

		comment.setLookStatus("0");
		comment.setCreateTime(new Date());

		comment.setName(u.getName());
		comment.setUid(Integer.parseInt(u.getUid()));
		comment.setRid(order.getRid());
		comment.setWord(word);
		System.out.println(comment);
		commentService.add(comment);
		return new Result(true,200,"成功");
	}
	@GetMapping("tousu")
	public Result toushu(Integer cid) throws CommonException {
		Comment comment = commentService.findByCid(cid);
		comment.setLookStatus("1");
		commentService.update(comment);
		return new Result().ok();

	}

}
