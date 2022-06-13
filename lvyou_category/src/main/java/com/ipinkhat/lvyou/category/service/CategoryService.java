package com.ipinkhat.lvyou.category.service;


import com.ipinkhat.lvyou.category.dao.CategoryDao;
import com.ipinkhat.lvyou.common.sevice.BaseService;
import com.ipinkhat.lvyou.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 服务层
 *
 * @author Administrator
 *
 */
@Service
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	public List<Category> findAll() {

		return categoryDao.selectList(null);
	}


    public Category findById(Long id) {
  return   categoryDao.selectById(id);
	}
}
