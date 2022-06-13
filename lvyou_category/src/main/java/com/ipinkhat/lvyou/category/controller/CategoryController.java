package com.ipinkhat.lvyou.category.controller;
import java.util.Map;

import com.ipinkhat.lvyou.category.service.CategoryService;
import com.ipinkhat.lvyou.common.entity.Result;
import com.ipinkhat.lvyou.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public Result findAll(){
		return new Result(true,200,"查询成功",categoryService.findAll());
	}

	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Category findById(@PathVariable("id")Long id){
		return categoryService.findById(id);
	}


}
